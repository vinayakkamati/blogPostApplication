package com.vinayak.blog.controllers;

import com.vinayak.blog.models.Comment;
import com.vinayak.blog.models.Post;
import com.vinayak.blog.services.CommentService;
import com.vinayak.blog.services.PostService;
import com.vinayak.blog.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    private final CommentService commentService;

    private final PostService postService;

    private final PostController postController;

    private final UserService userService;

    public CommentController(CommentService commentService, PostService postService,
                             PostController postControler, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.postController = postControler;
        this.userService = userService;
    }

    @ModelAttribute
    public void modelAttribute(Model model) {
        model.addAttribute("sessionUser", userService.findUserByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()));

        model.addAttribute("admin", hasRole("ADMIN"));
    }

    @GetMapping("/showNewCommentForm/{id}")
    public String showNewCommentForm(@PathVariable(value = "id") Integer id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "newComment";
    }

    @PostMapping("/comment")
    public String saveComment(@RequestParam("comment") String data,
                              @RequestParam("postId") String postId,
                              @RequestParam("name") String name,
                              @RequestParam("email") String email,
                              @RequestParam("commentId") String commentId,
                              Model model) {
        if (commentId != "") {
            int id = Integer.parseInt(commentId);
            this.commentService.deleteCommentByPostId(id);
        }
        int id = Integer.parseInt(postId);
        Comment comment = new Comment();
        comment.setComment(data);
        comment.setName(name);
        comment.setPostId(id);
        comment.setEmail(email);
        commentService.saveComment(comment);
        return postController.viewPost(id, model);
    }

    @DeleteMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable(value = "id") Integer id) {
        this.commentService.deleteCommentByPostId(id);
        return "redirect:/";
    }

    @GetMapping("/showCommentUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "UpdateComment";
    }

    public static boolean hasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }
}
