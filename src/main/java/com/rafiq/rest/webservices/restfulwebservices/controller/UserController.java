package com.rafiq.rest.webservices.restfulwebservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.rafiq.rest.webservices.restfulwebservices.dto.UserLocationDTO;
import com.rafiq.rest.webservices.restfulwebservices.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users-location")
    public List<UserLocationDTO> getAlLUsersLocation() {
        return userService.getAllUsersLocation();
    }
}
