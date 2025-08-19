## Profiling

application.properties

```bash
spring.profiles.active=dev   # sets the active profile
```

application-dev.properties

```bash
#==== connect to mysql ======#
spring.jpa.defer-datasource-initialization=true
spring.datasource.url=jdbc:mysql://localhost:3306/sns?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=12345678
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update

server.port=8081
```

application-prod.properties

```bash
#==== connect to mysql ======#
spring.jpa.defer-datasource-initialization=true
spring.datasource.url=jdbc:mysql://localhost:3306/sns?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=12345678
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update

server.port=8080
```

Now run

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```
