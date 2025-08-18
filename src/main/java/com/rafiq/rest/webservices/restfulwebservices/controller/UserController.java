package com.rafiq.rest.webservices.restfulwebservices.controller;

import java.util.List;

import com.rafiq.rest.webservices.restfulwebservices.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.rafiq.rest.webservices.restfulwebservices.dto.UserLocationDTO;
import com.rafiq.rest.webservices.restfulwebservices.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users-location")
    public List<UserLocationDTO> getAlLUsersLocation() {
        return userService.getAllUsersLocation();
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}
