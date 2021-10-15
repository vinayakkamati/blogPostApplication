package com.vinayak.blog.controllers;

import com.vinayak.blog.dto.UserDto;
import com.vinayak.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LogInRegistrationController {

    private final UserService userService;

    @Autowired
    private PostController postController;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ModelAttribute("user")
    public UserDto userDto(){
        return new UserDto();
    }

    public LogInRegistrationController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/registration")
    public String createNewUser(Model model) {

        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") UserDto userDto) {

        userService.saveUserDetails(userDto);
        return "registration";
    }
}
