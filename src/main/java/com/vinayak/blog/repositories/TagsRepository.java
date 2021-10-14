package com.vinayak.blog.repositories;

import com.vinayak.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagsRepository extends JpaRepository<Tag, Integer> {
    @Query(value = "SELECT * FROM tags t WHERE t.name LIKE ?1", nativeQuery = true )
    Tag findTagWithName(String name);
}
