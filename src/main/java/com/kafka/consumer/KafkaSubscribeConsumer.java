package com.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by tomar on 27/06/17.
 */
public class KafkaSubscribeConsumer {
    public static void main(String[] args) {


        Properties config = new Properties();
        config.put("bootstrap.servers", "localhost:9092,localhost:9093");
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("group.id","test");

        Consumer<String, String> consumer = new KafkaConsumer<String, String>(config);

        //Create Topic list to subscribe
        List<String> topics = new ArrayList<String>();
        topics.add("topic-1");
        topics.add("topic-2");
        consumer.subscribe(topics);

        // start consuming messages

        try {
            while (true) {
                final ConsumerRecords<String, String> records = consumer.poll(11);
                for (ConsumerRecord record : records) {
                    System.out.println(String.format("topic : %s, partition : %d, offset : %d, key : %s, value: %s ",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }


    }
}
