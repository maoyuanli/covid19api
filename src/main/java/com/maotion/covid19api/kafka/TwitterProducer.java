package com.maotion.covid19api.kafka;

import com.google.common.collect.Lists;
import com.maotion.covid19api.utils.TokenFetcher;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class TwitterProducer {

    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";
    private static final Logger LOGGER = LogManager.getLogger(TwitterProducer.class);
    List<String> terms = Lists.newArrayList("virus", "coronavirus", "covid19", "covid-19", "pandemic");

    public TwitterProducer() {
    }

    public void run() {
        BlockingQueue<String> msgQueue = new LinkedBlockingDeque<>(1000);
        Client hbClient = createTwitterClient(msgQueue);
        hbClient.connect();
        while(!hbClient.isDone()){
            String msg = null;
            try {
                msg = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                hbClient.stop();
            }
            if(msg!=null){
                LOGGER.debug(String.format("\nTweet : %s\n",msg));
            }
        }
    }

    public Client createTwitterClient(BlockingQueue<String> msgQueue) {

        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        hosebirdEndpoint.trackTerms(terms);

        TokenFetcher tokenFetcher = new TokenFetcher("token.json");
        String apiKey = tokenFetcher.fetchToken("api_key");
        String apiSecret = tokenFetcher.fetchToken("api_secret");
        String accessToken = tokenFetcher.fetchToken("access_token");
        String accessSecret = tokenFetcher.fetchToken("access_secret");
        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1(apiKey, apiSecret, accessToken, accessSecret);

        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        Client hosebirdClient = builder.build();
        return hosebirdClient;
    }

    public static void main(String[] args) {

        TwitterProducer twitterProducer = new TwitterProducer();
        twitterProducer.run();

        // properties
//        Properties properties = new Properties();
//        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
//        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//
//        // producer
//        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
//
//        // producer record
//        ProducerRecord<String, String> record = new ProducerRecord<>("topic1", "key1", "hello kafka from java");
//
//        // send data
//        producer.send(record, new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
//                if (e == null) {
//                    LOGGER.debug(String.format("\n-------\nTopic: %s \nPartition: %s " +
//                                    "\nOffset: %s \nTimestamp: %s\n-------",
//                            recordMetadata.topic(),
//                            recordMetadata.partition(),
//                            recordMetadata.offset(),
//                            recordMetadata.timestamp()
//                    ));
//                } else {
//                    LOGGER.error("Kafka Producer Error", e);
//                }
//            }
//        });
//        producer.flush();
//        producer.close();
    }

}