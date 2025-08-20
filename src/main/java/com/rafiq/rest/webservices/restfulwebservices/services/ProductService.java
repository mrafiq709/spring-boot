package com.rafiq.rest.webservices.restfulwebservices.services;

import org.springframework.stereotype.Service;

import com.rafiq.rest.webservices.restfulwebservices.dto.ProductHighlightResult;
import com.rafiq.rest.webservices.restfulwebservices.model.Product;
import com.rafiq.rest.webservices.restfulwebservices.repository.ProductRepository;
import com.rafiq.rest.webservices.restfulwebservices.repository.ProductSearchRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductSearchRepository searchRepository;

    public ProductService(ProductRepository repository, ProductSearchRepository searchRepository) {
        this.repository = repository;
        this.searchRepository = searchRepository;
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public List<Product> searchByName(String name) {
        return repository.findByName(name);
    }
    
    public List<Product> fullTextSearch(String text) {
        return repository.searchByText(text);
    }
    
    public List<ProductHighlightResult> searchWithHighlight(String text) {
        return searchRepository.searchWithHighlight(text);
    }
}

