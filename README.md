## Elastic Search

Add dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```

Run Elasticsearch locally

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
