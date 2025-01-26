package com.rafiq.rest.webservices.restfulwebservices.repository;

import com.rafiq.rest.webservices.restfulwebservices.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
