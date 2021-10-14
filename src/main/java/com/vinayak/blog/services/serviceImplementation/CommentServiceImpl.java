package com.vinayak.blog.services.serviceImplementation;

import com.vinayak.blog.models.Comment;
import com.vinayak.blog.repositories.CommentRepository;
import com.vinayak.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void saveComment(Comment comment) {
        this.commentRepository.save(comment);
    }

    @Override
    public void deleteCommentByPostId(Integer id) {
        this.commentRepository.deleteById(id);
    }

    @Override
    public Comment getCommentById(int id) {
        Optional<Comment> optional = commentRepository.findById(id);
        Comment comment = null;
        if (optional.isPresent()) {
            comment = optional.get();
        } else {
            throw new RuntimeException(" Comment not found for id :: " + id);
        }
        return comment;
    }
}