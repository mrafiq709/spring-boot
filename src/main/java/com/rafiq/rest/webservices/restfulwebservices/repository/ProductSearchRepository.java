package com.rafiq.rest.webservices.restfulwebservices.repository;

import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import com.rafiq.rest.webservices.restfulwebservices.dto.ProductHighlightResult;
import com.rafiq.rest.webservices.restfulwebservices.model.Product;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductSearchRepository {

    private final ElasticsearchOperations operations;

    public ProductSearchRepository(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    public List<ProductHighlightResult> searchWithHighlight(String text) {
        
    	Highlight highlight = new Highlight(
    			List.of(
    					new HighlightField("name"),
    					new HighlightField("description")
    			)
    	);
    	
    	HighlightQuery highlightQuery = new HighlightQuery(highlight, Product.class);
    	
    	Query query = new NativeQueryBuilder()
    			.withQuery(q -> q
    					.multiMatch(m -> m
    							.query(text)
    							.fields("name^2", "description")
    						)
    				)
    			.withHighlightQuery(highlightQuery)
    			.withPageable(PageRequest.of(0,10))
    			.build();

        SearchHits<Product> hits = operations.search(query, Product.class);

        return hits.stream()
                .map(hit -> new ProductHighlightResult(
                        hit.getContent(),
                        hit.getHighlightFields()
                ))
                .collect(Collectors.toList());
    }
}

