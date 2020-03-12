package com.maotion.covid19api.controllers;

import com.maotion.covid19api.entities.Tweets;
import com.maotion.covid19api.services.TweetsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TweetsController {

    private TweetsService tweetsService;

    public TweetsController(TweetsService tweetsService) {
        this.tweetsService = tweetsService;
    }

    @GetMapping("getalltweets")
    public List<Tweets> getAllTweets(){
        return tweetsService.findAll();
    }
}
