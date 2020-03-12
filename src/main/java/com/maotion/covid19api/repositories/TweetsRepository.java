package com.maotion.covid19api.repositories;

import com.maotion.covid19api.entities.Tweets;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetsRepository extends MongoRepository<Tweets, String> {
}
