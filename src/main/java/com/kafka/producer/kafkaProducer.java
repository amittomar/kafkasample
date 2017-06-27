package com.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * Created by tomar on 27/06/17.
 */
public class kafkaProducer {
    private static final String TOPIC ="my_topic_1";
    public static void main(String[] args) {


        Properties config = new Properties();
        config.put("bootstrap.servers","localhost:9092,localhost:9093");
        config.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(config);
        try {
            for (int i = 0; i < 200; i++) {

                ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC, Integer.toString(i), "My message" + Integer.toString(i));

                producer.send(record, new Callback() {
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        System.out.print("message sent");
                        if(exception != null){
                            exception.printStackTrace();
                        }
                    }
                });
              //  System.out.println("sent"+i);
            }
        }
               catch (Exception e){
                e.printStackTrace();
               }
               finally {
                  System.out.print("done");
           }

        }
    }

