package com.rafiq.rest.webservices.restfulwebservices.dto;

import java.util.List;
import java.util.Map;

import com.rafiq.rest.webservices.restfulwebservices.model.Product;

public class ProductHighlightResult {
    private Product product;
    private Map<String, List<String>> highlights;

    public ProductHighlightResult(Product product, Map<String, List<String>> highlights) {
        this.product = product;
        this.highlights = highlights;
    }

    public Product getProduct() {
        return product;
    }

    public Map<String, List<String>> getHighlights() {
        return highlights;
    }
}

