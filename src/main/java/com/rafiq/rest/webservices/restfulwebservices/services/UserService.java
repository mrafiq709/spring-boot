package com.rafiq.rest.webservices.restfulwebservices.services;
import com.rafiq.rest.webservices.restfulwebservices.dto.UserDTO;
import com.rafiq.rest.webservices.restfulwebservices.model.Location;
import com.rafiq.rest.webservices.restfulwebservices.model.Role;
import com.rafiq.rest.webservices.restfulwebservices.repository.LocationRepository;
import com.rafiq.rest.webservices.restfulwebservices.repository.RoleRepository;
import com.rafiq.rest.webservices.restfulwebservices.repository.UserRepository;
import com.rafiq.rest.webservices.restfulwebservices.dto.UserLocationDTO;
import com.rafiq.rest.webservices.restfulwebservices.model.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Assuming roles are loaded as a list
        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().getRoleName()) // Add role from database
                .build();
    }

    public List<UserLocationDTO> getAllUsersLocation() {
        return userRepository.findAll()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    private UserLocationDTO convertEntityToDTO(UserEntity user) {
        UserLocationDTO userLocationDTO = new UserLocationDTO();
        userLocationDTO.setUserId(user.getId());
        userLocationDTO.setEmail(user.getEmail());
        userLocationDTO.setPlace(user.getLocation().getPlace());
        userLocationDTO.setLongitude(user.getLocation().getLongitude());
        userLocationDTO.setLatitude(user.getLocation().getLatitude());
        return userLocationDTO;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertToDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setRole(user.getRole());
        userDTO.setLocation(user.getLocation());
        return userDTO;
    }

    public UserEntity createUser(String firstName, String lastName, String email, String username, String password, String role, String location) {
        Location location2 = new Location();
        location2.setPlace(location);
        location2.setDescription("Awesome");
        location2.setLongitude(40.5);
        location2.setLatitude(38.9);
        locationRepository.save(location2);
        Role role2 = roleRepository.findByRoleName(role);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        userEntity.setUsername(username);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setRole(role2);
        userEntity.setLocation(location2);
        return userRepository.save(userEntity);
    }
}


