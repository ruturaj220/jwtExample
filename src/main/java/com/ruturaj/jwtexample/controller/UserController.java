package com.ruturaj.jwtexample.controller;

import com.ruturaj.jwtexample.entity.User;
import com.ruturaj.jwtexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/getAll")
    public List<User> getUser(){
        return userService.getUsers();
    }
}
