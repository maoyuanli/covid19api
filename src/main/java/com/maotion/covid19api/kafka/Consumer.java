package com.maotion.covid19api.kafka;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;


public class Consumer {

    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";
    private static final Logger LOGGER = LogManager.getLogger(Consumer.class);
    private static final String GROUP = "group1";
    private static final String TOPIC = "twitter_topic";
    private static final int numOfMsgToRead = 30;
    private static final CountDownLatch LATCH = new CountDownLatch(1);
    private static int numOfMsgReadSoFar = 0;
    private static boolean keepReading = true;

    public static void main(String[] args) throws InterruptedException {


        ConsumerRunnable consumerRunnable = new ConsumerRunnable(LATCH);
        Thread thread = new Thread(consumerRunnable);
        thread.start();
        LATCH.await();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.debug("\nShudown Hook\n");
            consumerRunnable.shutdown();
            try {
                LATCH.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }


    public static class ConsumerRunnable implements Runnable {

        private CountDownLatch latch;
        private KafkaConsumer<String, String> consumer;

        public ConsumerRunnable(CountDownLatch latch) {
            this.latch = latch;

            Properties properties = new Properties();
            properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP);
            properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

            consumer = new KafkaConsumer<>(properties);
            consumer.subscribe(Arrays.asList(TOPIC));
        }

        @Override
        public void run() {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            MongoDatabase db = mongoClient.getDatabase("covid19");
            MongoCollection collection = db.getCollection("tweets");
            try {
                while (keepReading) {
                    ConsumerRecords<String, String> records =
                            consumer.poll(Duration.ofMillis(100));

                    records.forEach(record -> {
                        Document tweets = Document.parse(record.value());
                        collection.insertOne(tweets);
                        LOGGER.debug(String.format("\n-----\nKey: %s\nValue: %s\nPartition: %s\nOffiset: %s\n-----",
                                record.key(), record.value(), record.partition(), record.offset()));
                        numOfMsgReadSoFar += 1;
                        if (numOfMsgReadSoFar >= numOfMsgToRead) {
                            keepReading = false;
                        }
                    });
                }
            } catch (WakeupException e) {
                LOGGER.debug("\n-----\nShutdown Singal: \n" + e.getMessage());
            } finally {
                consumer.close();
                latch.countDown();
            }

        }

        public void shutdown() {
            consumer.wakeup();
        }
    }
}
