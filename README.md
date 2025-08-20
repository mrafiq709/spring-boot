## Elastic Search

Add dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```

Run Elasticsearch locally

If Elasticsearch docker container already in local then remove it before Run:

```bash
cd elastic-start-local && ./uninstall.sh
```

```bash
https://www.elastic.co/docs/solutions/search/run-elasticsearch-locally
curl -fsSL https://elastic.co/start-local | sh
```

Update application.properties

```bash
spring.elasticsearch.uris=http://localhost:9200
spring.elasticsearch.username=elastic
spring.elasticsearch.password=auK4FOaP # It will be shown in cmd while running elasticsearch in local
```

Elasticsearch url: http://localhost:9200/

Kibana url: http://localhost:5601/

Now Run spring boot application

```bash
mvn spring-boot:run
```

Test Elastic API

-   Create [Product](./src/main/java/com/rafiq/rest/webservices/restfulwebservices/model/Product.java) Entity
-   Create [ProductRepository](./src/main/java/com/rafiq/rest/webservices/restfulwebservices/repository/ProductRepository.java)
-   Create [ProductService](./src/main/java/com/rafiq/rest/webservices/restfulwebservices/services/ProductService.java)
-   Create [ProductController](./src/main/java/com/rafiq/rest/webservices/restfulwebservices/controller/ProductController.java)

```bash
POST /products
```

```json
{
	"name": "Laptop",
	"description": "Gaming Laptop",
	"price": 1200.0
}
```

```bash
GET /products/search?name=Laptop
```

### For full text search

Elasticsearch will:

-   Tokenize “laptop gaming” into keywords
-   Search across both name and description
-   Boost matches in name
-   Return sorted by relevance score

Update [ProductRepository](./src/main/java/com/rafiq/rest/webservices/restfulwebservices/repository/ProductRepository.java)

```java
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
```

Update [ProductService](./src/main/java/com/rafiq/rest/webservices/restfulwebservices/services/ProductService.java)

```java
public List<Product> fullTextSearch(String text) {
	return repository.searchByText(text);
}
```

Update [ProductController](./src/main/java/com/rafiq/rest/webservices/restfulwebservices/controller/ProductController.java)

```java
@GetMapping("/full-search")
public List<Product> fullSearch(@RequestParam String q) {
	return service.fullTextSearch(q);
}
```

```bash
POST /products
```

```json
{
	"name": "Gaming Laptop",
	"description": "High performance laptop for gaming",
	"price": 1200.0
}
```

```json
{
	"name": "Office Laptop",
	"description": "Lightweight laptop for business use",
	"price": 800.0
}
```

```bash
GET /products/full-search?q=laptop gaming
```

## For Highlighting search text

-   Create DTO ProductHighlightResult.java
-   Create Repository ProductSearchRepository.java
-   Update ProductService.java

```java
public List<ProductHighlightResult> searchWithHighlight(String text) {
	return searchRepository.searchWithHighlight(text);
}
```

-   Update ProductController.java

```java
@GetMapping("/search-highlight")
public List<ProductHighlightResult> searchWithHighlight(@RequestParam String q) {
	return service.searchWithHighlight(q);
}
```

Now Test:

```bash
GET /products/search-highlight?q=gaming
```

Response should be

```json
[
	{
		"product": {
			"id": "h8E8xZgBFkDxm-dHjS3Z",
			"name": "Gaming Laptop",
			"description": "High performance laptop for gaming",
			"price": 1200.0
		},
		"highlights": {
			"name": ["<em>Gaming</em> Laptop"]
		}
	}
]
```
