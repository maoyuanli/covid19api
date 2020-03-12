package com.maotion.covid19api.services;

import com.maotion.covid19api.entities.Tweets;
import com.maotion.covid19api.repositories.TweetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetsService {

    private TweetsRepository tweetsRepository;

    @Autowired
    public TweetsService(TweetsRepository tweetsRepository) {
        this.tweetsRepository = tweetsRepository;
    }

    public List<Tweets> findAll() {
        return tweetsRepository.findAll();
    }
}
