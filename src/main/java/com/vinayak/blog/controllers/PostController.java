package com.vinayak.blog.controllers;

import com.vinayak.blog.models.Comment;
import com.vinayak.blog.models.Post;
import com.vinayak.blog.models.Tag;
import com.vinayak.blog.models.User;
import com.vinayak.blog.repositories.CommentRepository;
import com.vinayak.blog.services.PostService;
import com.vinayak.blog.services.TagService;
import com.vinayak.blog.services.UserService;
import com.vinayak.blog.services.serviceImplementation.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserServiceImp userServiceImp;

    @ModelAttribute
    public void modelAttribute(Model model) {
        model.addAttribute("sessionUser", userService.findUserByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()));
        model.addAttribute("admin", hasRole("ADMIN"));
    }

    @RequestMapping("/search")
    public List<Post> searchResult(@RequestParam("search") String keyword, Model model) {
        return showHomePage(keyword, model);
//        return "keyword search";
    }


    @RequestMapping("/")
    public List<Post> showHomePage(String keyword, Model model) {
        return showPagination(1, "publishedAt", "desc", keyword, model);
    }

    @GetMapping("/page/{pageNo}")
    public List<Post>  showPagination(@PathVariable("pageNo") int pageNo,
                                 @RequestParam(value = "sortField", defaultValue = "publishedAt") String sortField,
                                 @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                 String keyword,
                                 Model model) {
        int pageSize = 4;
        Page<Post> page = postService.findPaginated(pageNo, pageSize, sortField, sortDir, keyword);
        List<Post> posts = page.getContent();
        List<Tag> tags = tagService.getAllTag();

        List<User> users = userService.getAllUser();

//        model.addAttribute("tags", tags);
//        model.addAttribute("users", users);
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("totalPage", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDir", sortDir);
//        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
//        if (model.getAttribute("posts") == null) {
//            model.addAttribute("posts", posts);
//        }
//        return "home";
        return posts;
    }

    @GetMapping("/showNewPostForm")
    public String showNewPostForm() {
        return "newpost";
    }

    @PostMapping("/savepost")
    public Post savePost(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("tags") String tags,
                           @RequestParam("author") String author) {

        Timestamp time = Timestamp.from(Instant.now());
        Post post = new Post();

//        User user = userServiceImp.getCurrentUser();

        post.setTitle(title);
        post.setAuthor(author);
        post.setContent(content);
        post.setPublished(true);
//        post.setAuthorId(user.getId());

        String[] tagArray = tags.split(" ");
        List<Tag> tagList = new ArrayList<Tag>();
        for (String tag : tagArray) {
            Tag tagObject = new Tag();
            if (tagService.checkTagWithName(tag)) {
                tagList.add(tagService.getTagByName(tag));
            } else {
                tagObject.setName(tag);
                tagObject.setCreatedAt(time);
                tagObject.setUpdatedAt(time);
                tagList.add(tagObject);
            }
        }
        post.setExcerpt(post.getContent().substring(0, 100));
        post.setTags(tagList);

        postService.savePost(post);
//        return "redirect:/";
        return post;
    }

    @GetMapping("post{postId}")
    public Post viewPost(@PathVariable("postId") int postId, Model model) {
        Post post = postService.getPostById(postId);
        List<Comment> comment = commentRepository.getCommentByPostId(postId);
        model.addAttribute("post", post);
        model.addAttribute("comment", comment);
//        return "ViewPost";
        return post;
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "UpdatePost";
    }

    @PutMapping("/updatePost")
    public String updatePost(@RequestParam(value = "title") String title,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "id") int postId,
                             @RequestParam("tags") String tags,
                             Model model) {
        Post post = postService.getPostById(postId);
        Timestamp time = Timestamp.from(Instant.now());

        post.setContent(content);
        post.setTitle(title);
        post.setUpdatedAt(time);

        String[] tagArray = tags.split(" ");
        List<Tag> tagList = new ArrayList<Tag>();
        for (String tag : tagArray) {
            Tag tagObject = new Tag();
            if (tagService.checkTagWithName(tag)) {
                tagList.add(tagService.getTagByName(tag));
            } else {
                tagObject.setName(tag);
                tagObject.setCreatedAt(time);
                tagObject.setUpdatedAt(time);
                tagList.add(tagObject);
            }
        }
        post.setExcerpt(post.getContent().substring(0, 100));
        post.setTags(tagList);

        postService.savePost(post);
//        return viewPost(postId, model);
        return "post updated";
    }

    @DeleteMapping("/deletePost/{id}")
    public String deletePost(@PathVariable(value = "id") int id) {
        this.postService.deletePostById(id);
//        return "redirect:/";
        return "post deleted";
    }


    @GetMapping("/filter")
    public List<Post> getFilteredPosts(@RequestParam(value = "authorId", required = false) Optional<Integer> authorId,
                                   @RequestParam(value = "tagId", required = false) Optional<Integer> tagId,
                                   String keyword, Model model) {

        if (authorId.isPresent()) {
            List<Integer> authorIds = Collections.singletonList(authorId.get());
            List<Integer> postIdByAuthorId = postService.getPostIdByAuthor(authorIds);
            if (tagId.isPresent()) {
                List<Integer> tagIds = Collections.singletonList(tagId.get());
                List<Integer> postsByTagId = postService.getAllPostIdByTagId(tagIds);
                List<Integer> postIds = new ArrayList<>();
                for (int postId : postsByTagId) {
                    if (postIdByAuthorId.contains(postId)) {
                        postIds.add(postId);
                    }
                }
                List<Post> posts = postService.getAllPostsById(postIds);
                model.addAttribute("posts", posts);
            } else {
                List<Post> posts = postService.getAllPostsById(postIdByAuthorId);
                model.addAttribute("posts", posts);
            }
        } else {
            List<Integer> tagIds = Collections.singletonList(tagId.get());
            List<Integer> postsByTagId = postService.getAllPostIdByTagId(tagIds);
            List<Post> posts = postService.getAllPostsById(postsByTagId);
            model.addAttribute("posts", posts);
        }
//        return showHomePage(keyword, model);
        return (List<Post>) model.getAttribute("posts");
    }


    public static boolean hasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }
}
