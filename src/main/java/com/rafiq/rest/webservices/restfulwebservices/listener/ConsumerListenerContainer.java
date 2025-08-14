package com.rafiq.rest.webservices.restfulwebservices.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerListenerContainer {
    @KafkaListener(topics = "user-location", groupId = "location_grp_id")
    public void consumeUserLocation(String msg) {
        System.out.println(msg);
    }
}