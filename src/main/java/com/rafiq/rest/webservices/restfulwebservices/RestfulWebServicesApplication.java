package com.rafiq.rest.webservices.restfulwebservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rafiq.rest.webservices.restfulwebservices.model.Location;
import com.rafiq.rest.webservices.restfulwebservices.model.Role;
import com.rafiq.rest.webservices.restfulwebservices.model.UserEntity;
import com.rafiq.rest.webservices.restfulwebservices.repository.LocationRepository;
import com.rafiq.rest.webservices.restfulwebservices.repository.RoleRepository;
import com.rafiq.rest.webservices.restfulwebservices.repository.UserRepository;

@SpringBootApplication
public class RestfulWebServicesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
//		Location location = new Location();
//		location.setPlace("Japan");
//		location.setDescription("Awesome");
//		location.setLongitude(40.5);
//		location.setLatitude(38.9);
//
//		locationRepository.save(location);
//
//		Role role = roleRepository.findByRoleName("USER");
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//		UserEntity userEntity = new UserEntity();
//		userEntity.setFirstName("Md Rafiqul");
//		userEntity.setLastName("Islam");
//		userEntity.setEmail("mrafiq709@gmail.com");
//		userEntity.setUsername("mrafiq709");
//		userEntity.setPassword(passwordEncoder.encode("secret"));
//		userEntity.setRole(role);
//		userEntity.setLocation(location);
//		userRepository.save(userEntity);
	}

}
