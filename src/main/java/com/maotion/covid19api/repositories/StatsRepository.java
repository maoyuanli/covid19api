package com.maotion.covid19api.repositories;

import com.maotion.covid19api.entities.Stats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatsRepository extends MongoRepository<Stats, String> {
}
