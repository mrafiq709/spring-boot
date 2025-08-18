## Enable Actuator APIs

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Update application.properties for actuator custom url
default actuator url is <b>/actuator</b>

```bash
#########################
## MANAGEMENT          ##
#########################
management.endpoints.web.base-path=/management
management.endpoints.web.exposure.include=*
#management.endpoints.web.exposure.exclude=beans
```

Update Security config to allow <b>/management</b>

```java
http.authorizeHttpRequests(
        auth -> auth
        .requestMatchers("/management/**").permitAll()
        .anyRequest().authenticated()
```

Now go to http://locahost:8080/management to see json data of actuator.
Add additional properties for Jmx and info

```bash
spring.application.name=RestFull APIs

spring.jmx.enabled=true
management.endpoints.jmx.exposure.include=*

management.info.env.enabled=true
info.app.name=${spring.application.name}
info.app.links.youtube=https://www.youtube.com/@kanezi
info.app.links.github=https://github.com/kanezi/spring-social-2-cloud

management.health.probes.enabled=true
```

Update pom.xml to show build info

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <executions>
                <execution>
                    <goals>
                        <goal>build-info</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

add Prometheus and postgreSQL dependency

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

postgreSQL application.properties

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/ss2c
spring.datasource.username=user
spring.datasource.password=pass
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

Now go to http://locahost:8080/management to prometheus APIs

## Grafana

.env

```bash
DB_SCHEMA=ss2c
DB_USER=user
DB_PASS=pass
```

1. PostgreSQL docker-compose.yml

```yml
services:
    db:
        image: postgres:13
        ports:
            - 5432:5432
        environment:
            POSTGRES_DB: ${DB_SCHEMA:ss2c}
            POSTGRES_USER: ${DB_USER:user}
            POSTGRES_PASSWORD: ${DB_PASS:pass}
```

docker-compose up -d

2. docker-compose docker/monitoring.yml

```yml
version: "3.8"

services:
    prometheus:
        image: prom/prometheus
        ports:
            - 9090:9090
        volumes:
            - ./prometheus/prometheus.yml:/etc/prometheus/prometheus.yml:ro
            - ./prometheus/alerting-rules.yml:/etc/prometheus/alerting-rules.yml

    postgres-exporter:
        image: bitnami/postgres-exporter
        ports:
            - 9187:9187
        environment:
            DATA_SOURCE_NAME: "postgresql://${DB_USER:-user}:${DB_PASS:-pass}@host.docker.internal:5432/${DB_SCHEMA:-ss2c}?sslmode=disable"

    grafana:
        image: grafana/grafana:9.5.2
        ports:
            - 3000:3000
        volumes:
            - ./grafana/provisioning/:/etc/grafana/provisioning/

    alertmanager:
        image: prom/alertmanager
        ports:
            - 9093:9093
        volumes:
            - ./alert-manager/email-alert.yml:/etc/alertmanager/config.yml
        command:
            - --config.file=/etc/alertmanager/config.yml
            - --storage.path=/alertmanager

    mailhog:
        image: mailhog/mailhog:v1.0.1
        ports:
            - 1025:1025
            - 8025:8025
```

## Create required files

/docker/prometheus.yml

```yml
global:
    scrape_interval: 15s
    evaluation_interval: 15s
scrape_configs:
    - job_name: prometheus
      static_configs:
          - targets: ["localhost:9090"]

    - job_name: postgres-exporter
      static_configs:
          - targets: ["postgres-exporter:9187"]

    - job_name:
          ss2c
          # Override the global default and scrape targets from this job every 5 seconds.
      scrape_interval: 5s
      metrics_path: /management/prometheus
      static_configs:
          - targets:
                - host.docker.internal:8080

alerting:
    alertmanagers:
        - scheme: http
          static_configs:
              - targets: ["alertmanager:9093"]

rule_files:
    - alerting-rules.yml
```

/docker/alerting-rules.yml

```yml
groups:
    - name: service_is_down
      rules:
          - alert: service_is_down
            expr: up == 0
            for: 1m
            labels:
                severity: critical
            annotations:
                summary: Service(s) are down
```

/docker/grafana/provisioning/datasources/datasource.yml

```yml
apiVersion: 1
deleteDatasources:
    - name: Prometheus
      orgId: 1
datasources:
    - name: Prometheus
      type: prometheus
      access: proxy
      url: http://prometheus:9090
      orgId: 1
```

/docker/alert-manager/email-alert.yml

```yml
global:
    # The smarthost and SMTP sender used for mail notifications.
    smtp_smarthost: "mailhog:1025"
    smtp_from: "alertmanager@example.org"
    smtp_require_tls: false

# The root route on which each incoming alert enters.
route:
    # The root route must not have any matchers as it is the entry point for
    # all alerts. It needs to have a receiver configured so alerts that do not
    # match any of the sub-routes are sent to someone.
    receiver: "unmatched-default-root-route"

    # When a new group of alerts is created by an incoming alert, wait at
    # least 'group_wait' to send the initial notification.
    # This way ensures that you get multiple alerts for the same group that start
    # firing shortly after another are batched together on the first
    # notification.
    group_wait: 30s

receivers:
    - name: "unmatched-default-root-route"
      email_configs:
          - to: "devopsteam@example.org"
            send_resolved: true
```

Now up Docker containers

```bash
docker-compose -f /docker/monitoring.yml up
```

Now hit

-   http://localhost:9090 for prometheus
-   http://localhost:9187 for postgrSQL Exporter
-   http://localhost:8025 for mail-hog
-   http://localhost:3000 for grafana

## For setup grafana

-   Premrtheus is connected by default

```bash
http://localhost:3000/connections/your-connections/datasources
```

For new dashboard, Go to grafana and copy the selected URL of the dashboard

```bash
https://grafana.com/grafana/dashboards/11378-justai-system-monitor/
```

Go to http://localhost:3000/dashboard/import

Paste and Click Load
then write a name for dasboard and import.
