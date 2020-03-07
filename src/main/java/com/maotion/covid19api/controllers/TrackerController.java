package com.maotion.covid19api.controllers;

import com.maotion.covid19api.entities.Stats;
import com.maotion.covid19api.services.TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TrackerController {

    private TrackerService trackerService;

    @Autowired
    public TrackerController(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    @PostMapping("reportcase")
    public Stats reportCase(@RequestBody Stats stats) {
        return this.trackerService.insert(stats);
    }

    @GetMapping("getallcase")
    public List<Stats> getAllCase() {
        return this.trackerService.findAll();
    }

    @DeleteMapping("deletecase")
    public List<Stats> delete(@RequestParam String country) {
        return this.trackerService.delete(country);
    }

    @GetMapping("findbycountry/{country}")
    public List<Stats> findByCountry(@PathVariable String country) {
        return this.trackerService.findByCountry(country);
    }
}
