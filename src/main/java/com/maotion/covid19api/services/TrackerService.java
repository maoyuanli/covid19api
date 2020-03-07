package com.maotion.covid19api.services;

import com.maotion.covid19api.entities.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackerService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public TrackerService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Stats save(Stats stats) {
        return mongoTemplate.save(stats);
    }

    public List<Stats> findAll() {
        return mongoTemplate.findAll(Stats.class);
    }

    public void delete(String country) {
        mongoTemplate.remove(new Query(Criteria.where("countryOrRegion").is(country)), Stats.class);
    }
}
