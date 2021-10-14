package com.vinayak.blog.services.serviceImplementation;

import com.vinayak.blog.models.Post;
import com.vinayak.blog.repositories.PostRepositories;
import com.vinayak.blog.repositories.PostTagRepository;
import com.vinayak.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepositories postRepository;

    @Autowired
    private PostTagRepository postTagRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void savePost(Post post) {
        this.postRepository.save(post);
    }

    @Override
    public Post getPostById(int id) {
        Optional<Post> optional = postRepository.findById(id);
        Post post = null;
        if (optional.isPresent()) {
            post = optional.get();
        } else {
            throw new RuntimeException(" Post not found for id :: " + id);
        }
        return post;
    }

    @Override
    public void deletePostById(int id) {
        this.postRepository.deleteById(id);
    }

    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String keyword) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        if (keyword != null) {
            return postRepository.findAll(keyword, pageable);
        }
        return postRepository.findAll(pageable);
    }

    public List<Post> findPostWithSorting(String field) {
        return postRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    @Override
    public List<Integer> getPostIdByAuthor(List<Integer> authorIds) {
        List<Integer> postIds = new ArrayList<>();
        for (int authorId : authorIds) {
            postIds.addAll(postRepository.getPostIdsByAuthorIds(authorId));
        }
        return postIds;
    }

    @Override
    public List<Integer> getAllPostIdByTagId(List<Integer> tagIds) {
        List<Integer> postId = new ArrayList<>();
        for (int tagId : tagIds) {
            postId.addAll(postTagRepository.getPostIdByTagId(tagId));
        }
        return postId;
    }

    @Override
    public List<Post> getAllPostsById(List<Integer> postIds) {
        return postRepository.findAllById(postIds);
    }
}