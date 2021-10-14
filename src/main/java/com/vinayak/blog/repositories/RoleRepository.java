package com.vinayak.blog.repositories;

import com.vinayak.blog.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}

