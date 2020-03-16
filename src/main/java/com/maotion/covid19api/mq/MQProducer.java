package com.maotion.covid19api.mq;

import com.maotion.covid19api.entities.Stats;
import com.maotion.covid19api.services.StatsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class MQProducer implements CommandLineRunner {

    private RabbitTemplate rabbitTemplate;
    private StatsService statsService;

    @Autowired
    public MQProducer(RabbitTemplate rabbitTemplate, StatsService statsService) {
        this.rabbitTemplate = rabbitTemplate;
        this.statsService = statsService;
    }


    @Override
    public void run(String... args) throws Exception {
        List<Stats> statsList = statsService.findAll();
        statsList.forEach(stats -> {
            rabbitTemplate.convertAndSend(MQConfig.QUEUE_NAME, stats);
//            rabbitTemplate.convertAndSend("Covid19StatsExchange", "RKey19", stats);
        });
    }
}
