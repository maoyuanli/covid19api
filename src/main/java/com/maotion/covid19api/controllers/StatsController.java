package com.maotion.covid19api.controllers;

import com.maotion.covid19api.entities.Stats;
import com.maotion.covid19api.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class StatsController {

    private StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping("reportcase")
    public Stats reportCase(@RequestBody Stats stats) {
        return this.statsService.insert(stats);
    }

    @GetMapping("getallcase")
    public List<Stats> getAllCase() {
        return this.statsService.findAll();
    }

    @DeleteMapping("deletecase")
    public List<Stats> delete(@RequestParam String country) {
        return this.statsService.delete(country);
    }

    @GetMapping("findbycountry/{country}")
    public List<Stats> findByCountry(@PathVariable String country) {
        return this.statsService.findByCountry(country);
    }
}
