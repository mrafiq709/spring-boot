package com.rafiq.rest.webservices.restfulwebservices.dto;

import com.rafiq.rest.webservices.restfulwebservices.model.Location;
import com.rafiq.rest.webservices.restfulwebservices.model.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private Location location;
}
