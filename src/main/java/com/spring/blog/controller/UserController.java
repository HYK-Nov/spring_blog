package com.spring.blog.controller;

import com.spring.blog.entity.User;
import com.spring.blog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/signup")
    public String signupForm(){
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(User user){
        usersService.save(user);
        return "redirect:/login";
    }
}
