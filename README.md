##### pom.xml
```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka-test</artifactId> <scope>test</scope>
</dependency>
```
##### Extract kafka_2.13-3.9.1.tgz
```bash
https://kafka.apache.org/downloads
tar -xzf kafka_2.13-3.9.1.tgz
cd kafka_2.13-3.9.1
```
##### Start Zookeeper and Kafka
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties # run in a separate terminal
bin/kafka-server-start.sh config/server.properties # run in a separate terminal
```
##### Kafka config: Check this dir
```bash
com/rafiq/rest/webservices/restfulwebservices/config
```
##### Kafka Producer: check this API
```java
    @PostMapping("/location")
    public String userLocation(@RequestBody UserLocationDTO dto) throws JsonProcessingException {
        return userService.notifyUser(dto);
    }
```

##### Kafka consumer: check this listener
```bash
com/rafiq/rest/webservices/restfulwebservices/listener/ConsumerListenerContainer.java
```

##### Update application.properties
```properties
#==== kafka ======#
spring.kafka.bootstrap-servers=localhost:9092 
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
```
##### To check the messages in the topic
```bash
# Run this command in a separate terminal
bin/kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic transaction-producer-topic \
  --from-beginning \
  --property print.key=true \
  --property print.value=true \
  --property print.timestamp=true \
  --property print.partition=true \
  --formatter kafka.tools.DefaultMessageFormatter
```