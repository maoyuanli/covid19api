package com.maotion.covid19api.mq;

import com.maotion.covid19api.entities.Stats;
import com.maotion.covid19api.services.TrackerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class MessageMQ implements CommandLineRunner {

    private RabbitTemplate rabbitTemplate;
    private TrackerService trackerService;

    @Autowired
    public MessageMQ (RabbitTemplate rabbitTemplate,TrackerService trackerService){
        this.rabbitTemplate = rabbitTemplate;
        this.trackerService = trackerService;
    }


    @Override
    public void run(String... args) throws Exception {
        List<Stats> statsList = trackerService.findAll();
        statsList.forEach(stats -> {
            rabbitTemplate.convertAndSend("Covid19StatsExchange", "RKey19", stats.toString());
        });
    }
}
