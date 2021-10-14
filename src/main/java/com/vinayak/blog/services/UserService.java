package com.vinayak.blog.services;

import com.vinayak.blog.dto.UserDto;
import com.vinayak.blog.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findUserByEmail(String email);

    void saveUserDetails(UserDto userDto);

    List<User> getAllUser();
}
