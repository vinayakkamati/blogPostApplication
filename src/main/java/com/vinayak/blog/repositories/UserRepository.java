package com.vinayak.blog.repositories;

import com.vinayak.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(@Param("email") String email);

//    @Query("SELECT u FROM User u WHERE u.email LIKE ?1")
//    User findByEmail(String email);

}

