package com.maotion.covid19api.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String QUEUE_NAME = "Covid19MQ1";
    private MQListener mqListener;

    @Autowired
    public MQConfig(MQListener mqListener) {
        this.mqListener = mqListener;
    }

    @Bean
    Queue createQueue(){
        return new Queue(QUEUE_NAME,true);
    }

    @Bean
    ConnectionFactory createConnectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }

    @Bean
    MessageListenerContainer createListenerContainer(){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(createConnectionFactory());
        container.setQueues(createQueue());
        container.setMessageListener(mqListener);
        return container;
    }
}
