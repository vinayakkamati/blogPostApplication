package com.vinayak.blog.services;

import com.vinayak.blog.models.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    List<Comment> getAllComments();

    void saveComment(Comment comment);

    void deleteCommentByPostId(Integer id);

    Comment getCommentById(int id);
}

