package com.rafiq.rest.webservices.restfulwebservices.controller;

import org.springframework.web.bind.annotation.*;

import com.rafiq.rest.webservices.restfulwebservices.model.Product;
import com.rafiq.rest.webservices.restfulwebservices.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String name) {
        return service.searchByName(name);
    }
}

