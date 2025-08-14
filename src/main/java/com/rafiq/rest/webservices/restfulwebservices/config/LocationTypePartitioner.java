package com.rafiq.rest.webservices.restfulwebservices.config;

import java.util.Map;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class LocationTypePartitioner implements Partitioner {
    @Override
    public void configure(Map<String, ?> configs) { // TODO Auto-generated method stub
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        int partition = cluster.partitionCountForTopic(topic);
        switch (String.valueOf(key)) {
            case "Okinawa": {
                return 1 % partition;
            }
            case "Tokyo": {
                return 2 % partition;
            }
            case "Nagoya": {
                return 3 % partition;
            }
            default:
                return 0;
        }
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }
}