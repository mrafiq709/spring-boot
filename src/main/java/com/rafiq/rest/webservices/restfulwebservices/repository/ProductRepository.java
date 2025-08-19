package com.rafiq.rest.webservices.restfulwebservices.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.rafiq.rest.webservices.restfulwebservices.model.Product;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    List<Product> findByName(String name);
    /**
     * multi_match → searches in multiple fields
     * name^2 → gives the name field double weight for relevance scoring
     * Results will be automatically sorted by _score (highest relevance first)
     */
    @Query("""
	{
		"multi_match": {
    		"query": "?0",
    		"fields": ["name^2", "description"]
		}
	}
	""")
    List<Product> searchByText(String text);
}

