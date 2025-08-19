package com.rafiq.rest.webservices.restfulwebservices.services;

import org.springframework.stereotype.Service;

import com.rafiq.rest.webservices.restfulwebservices.model.Product;
import com.rafiq.rest.webservices.restfulwebservices.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public List<Product> searchByName(String name) {
        return repository.findByName(name);
    }
}

