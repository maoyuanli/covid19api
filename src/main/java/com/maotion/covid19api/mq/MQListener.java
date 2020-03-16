package com.maotion.covid19api.mq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
public class MQListener implements MessageListener {

    private final Logger LOGGER = LogManager.getLogger(MQListener.class);

    @Override
    public void onMessage(Message message) {
        LOGGER.debug(String.format("MQListener - Message - : %s",message.getBody()));
    }
}
