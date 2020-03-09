package com.maotion.covid19api.batch;

import com.maotion.covid19api.entities.Stats;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public class Writer implements ItemWriter<List<Stats>> {

    @Autowired
    MongoRepository mongoRepository;

    @Override
    public void write(List<? extends List<Stats>> list) throws Exception {
        mongoRepository.insert(list);
    }
}
