package com.vinayak.blog.repositories;

import com.vinayak.blog.models.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag,Integer> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM post_tags pt WHERE pt.post_id = ?1", nativeQuery = true)
    void deleteTagByPostId(int id);

    @Query(value = "SELECT pt.post_id FROM post_tags pt WHERE pt.tag_id = ?1",nativeQuery = true)
    List<Integer> getPostIdByTagId(int tagId);
}
