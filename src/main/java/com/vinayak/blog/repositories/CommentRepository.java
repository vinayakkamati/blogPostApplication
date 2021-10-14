package com.vinayak.blog.repositories;

import com.vinayak.blog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c WHERE c.postId = ?1")
    List<Comment> getCommentByPostId(int post_id);
}

