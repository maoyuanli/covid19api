package com.maotion.covid19api.repositories;

import com.maotion.covid19api.entities.Stats;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StatsRepository extends MongoRepository<Stats, String> {
    List<Stats> deleteAllByCountryContains(String country);

    List<Stats> findAllByCountryContains(String country);
}
