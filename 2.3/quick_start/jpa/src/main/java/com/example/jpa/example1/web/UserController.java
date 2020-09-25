package com.example.jpa.example1.web;

import com.example.jpa.example1.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("user")
    public User get(@RequestBody User user) {
        return user;
    }
}
