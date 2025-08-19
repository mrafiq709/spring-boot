package com.rafiq.rest.webservices.restfulwebservices.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.rafiq.rest.webservices.restfulwebservices.model.Product;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    List<Product> findByName(String name);
}

