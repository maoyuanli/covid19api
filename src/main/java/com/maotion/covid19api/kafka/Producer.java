package com.maotion.covid19api.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class Producer {

    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";
    private static final Logger LOGGER = LogManager.getLogger(Producer.class);

    public static void main(String[] args) {

        // properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // producer record
        ProducerRecord<String, String> record = new ProducerRecord<>("topic1", "key1", "hello kafka from java");

        // send data
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    LOGGER.debug(String.format("-------\nTopic: %s \nPartition: %s " +
                                    "\nOffset: %s \nTimestamp: %s\n-------",
                            recordMetadata.topic(),
                            recordMetadata.partition(),
                            recordMetadata.offset(),
                            recordMetadata.timestamp()
                    ));
                } else {
                    LOGGER.error("Kafka Producer Error", e);
                }
            }
        });
        producer.flush();
        producer.close();
    }

}
