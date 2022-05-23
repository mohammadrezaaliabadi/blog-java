package com.example.blog.module.users.controller;

import com.example.blog.module.users.model.Users;
import com.example.blog.module.users.service.UsersService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = {"/",""}, method = RequestMethod.GET)
    public List<Users> getUsers() {
        return usersService.findAllUsers();
    }

    @RequestMapping(value = {"/",""}, method = RequestMethod.POST)
    public Users registerUser(@RequestBody Users users) {
        return usersService.registerUser(users);
    }

}
