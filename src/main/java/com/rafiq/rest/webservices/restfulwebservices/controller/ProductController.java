package com.rafiq.rest.webservices.restfulwebservices.controller;

import org.springframework.web.bind.annotation.*;

import com.rafiq.rest.webservices.restfulwebservices.dto.ProductHighlightResult;
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
    
    @GetMapping("/full-search")
    public List<Product> fullSearch(@RequestParam String q) {
        return service.fullTextSearch(q);
    }
    
    @GetMapping("/search-highlight")
    public List<ProductHighlightResult> searchWithHighlight(@RequestParam String q) {
        return service.searchWithHighlight(q);
    }
}

