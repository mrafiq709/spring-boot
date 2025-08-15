package com.rafiq.rest.webservices.restfulwebservices.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import com.rafiq.rest.webservices.restfulwebservices.repository.UserRepository;

import lombok.Value;

@Component
@Value
public class AppUserInfoContributor implements InfoContributor {

	@Autowired
	UserRepository repository;
	
	@Override
	public void contribute(Builder builder) {
		builder.withDetail("app-user.stats", Map.of("count", repository.count())).build();
	}

}
