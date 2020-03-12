package com.maotion.covid19api.persistence;

import com.maotion.covid19api.kafka.TwitterConsumer;
import com.maotion.covid19api.kafka.TwitterProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TweetsCMDRunner implements CommandLineRunner {

    private TwitterConsumer twitterConsumer;
    private TwitterProducer twitterProducer;

    @Autowired
    public TweetsCMDRunner(TwitterConsumer twitterConsumer, TwitterProducer twitterProducer) {
        this.twitterConsumer = twitterConsumer;
        this.twitterProducer = twitterProducer;
    }

    @Override
    public void run(String... args) throws Exception {
        twitterProducer.run();
        twitterConsumer.run();

    }
}
