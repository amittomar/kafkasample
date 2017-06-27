package com.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by tomar on 27/06/17.
 */
public class KafkaAssignConsumer {

    public static void main(String[] args) {


        Properties config = new Properties();
        config.put("bootstrap.servers", "localhost:9092,localhost:9093");
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<String, String>(config);

        //Create Partitions list to subscribe
        List<TopicPartition> partitions = new ArrayList<TopicPartition>();
        partitions.add(new TopicPartition("topic-1",0));
        partitions.add( new TopicPartition("topic-2",1));
        consumer.assign(partitions);

        // start consuming messages

        try {
            while (true) {
                final ConsumerRecords<String, String> records = consumer.poll(10);
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
