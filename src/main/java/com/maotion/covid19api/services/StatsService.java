package com.maotion.covid19api.services;

import com.maotion.covid19api.entities.Stats;
import com.maotion.covid19api.repositories.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {

    private StatsRepository statsRepository;

    @Autowired
    public StatsService(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public Stats insert(Stats stats) {
        return statsRepository.insert(stats);
    }

    public List<Stats> findAll() {
        return statsRepository.findAll();
    }

    public List<Stats> delete(String country) {
        return this.statsRepository.deleteAllByCountryContains(country);
    }

    public List<Stats> findByCountry(String country) {
        return this.statsRepository.findAllByCountryContains(country);
    }
}
