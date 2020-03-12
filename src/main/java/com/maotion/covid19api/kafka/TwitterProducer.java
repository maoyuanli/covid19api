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
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@Component
public class TwitterProducer {

    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";
    private static final Logger LOGGER = LogManager.getLogger(TwitterProducer.class);
    List<String> terms = Lists.newArrayList("coronavirus", "covid19", "covid-19");
    private static int tweetsCount = 20;

    public TwitterProducer() {
    }

    public void run() {
        KafkaProducer<String, String> kafkaProducer = createKafkaProducer();
        BlockingQueue<String> msgQueue = new LinkedBlockingDeque<>(1000);
        Client hbClient = createTwitterClient(msgQueue);
        hbClient.connect();
        while (!hbClient.isDone() && tweetsCount > 0) {
            String msg = null;
            try {
                msg = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                hbClient.stop();
            }
            if (msg != null) {
                LOGGER.debug(String.format("\nTweet : %s\n", msg));
                tweetsCount--;
                kafkaProducer.send(new ProducerRecord<>("twitter_topic", null, msg), (recordMetadata, e) -> {
                    if (e != null) {
                        LOGGER.debug(String.format("Twitter Producer Error : %s", e.getMessage()));
                    }
                });
            }
        }
    }

    public Client createTwitterClient(BlockingQueue<String> msgQueue) {

        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        hosebirdEndpoint.trackTerms(terms);

        TokenFetcher tokenFetcher = new TokenFetcher("token.json");
        String apiKey = tokenFetcher.fetchToken("api_key");
        String apiSecret = tokenFetcher.fetchToken("api_secret");
        String accessToken = tokenFetcher.fetchToken("access_token");
        String accessSecret = tokenFetcher.fetchToken("access_secret");
        Authentication hosebirdAuth = new OAuth1(apiKey, apiSecret, accessToken, accessSecret);

        ClientBuilder builder = new ClientBuilder()
                .name("tweet-client-covid19")
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        return builder.build();
    }

    public KafkaProducer<String, String> createKafkaProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(properties);
    }

}
