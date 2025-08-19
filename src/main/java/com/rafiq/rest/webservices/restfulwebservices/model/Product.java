package com.rafiq.rest.webservices.restfulwebservices.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private double price;
}

