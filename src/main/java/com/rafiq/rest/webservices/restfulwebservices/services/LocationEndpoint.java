package com.rafiq.rest.webservices.restfulwebservices.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import com.rafiq.rest.webservices.restfulwebservices.repository.LocationRepository;

import lombok.Value;

@Endpoint(id = "user-loaction")
@Component
@Value
public class LocationEndpoint {
	@Autowired
	LocationRepository locationRepository;
	
	@ReadOperation
	Map<String, Long> count() {
		return Map.of("count", locationRepository.count());
	}
}
