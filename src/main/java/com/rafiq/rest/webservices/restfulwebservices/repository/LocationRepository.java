package com.rafiq.rest.webservices.restfulwebservices.repository;

import com.rafiq.rest.webservices.restfulwebservices.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
