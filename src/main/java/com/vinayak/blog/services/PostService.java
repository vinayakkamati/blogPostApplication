package com.vinayak.blog.services;

import com.vinayak.blog.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    List<Post> getAllPosts();

    void savePost(Post post);

    Post getPostById(int id);

    void deletePostById(int id);

    Page<Post> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String keyword);

    List<Integer> getPostIdByAuthor(List<Integer> authorIds);

    List<Integer> getAllPostIdByTagId(List<Integer> tagIds);

    List<Post> getAllPostsById(List<Integer> postIds);
}