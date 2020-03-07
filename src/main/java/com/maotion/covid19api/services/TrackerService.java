package com.maotion.covid19api.services;

import com.maotion.covid19api.entities.Stats;
import com.maotion.covid19api.repositories.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackerService {

    private StatsRepository statsRepository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public TrackerService(StatsRepository statsRepository, MongoTemplate mongoTemplate) {
        this.statsRepository = statsRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Stats insert(Stats stats) {
        return statsRepository.insert(stats);
    }

    public List<Stats> findAll() {
        return statsRepository.findAll();
    }

    public void delete(String country) {
        mongoTemplate.remove(new Query(Criteria.where("countryOrRegion").is(country)), Stats.class);
    }
}
