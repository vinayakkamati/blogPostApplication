package com.vinayak.blog.repositories;

import com.vinayak.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users WHERE email LIKE ?1", nativeQuery = true)
    User findByEmail(String email);
}