package com.maotion.covid19api.persistence;

import com.maotion.covid19api.entities.Stats;
import com.maotion.covid19api.utils.DailyReportUrlGenerator;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

@Component
@Order(1)
public class DailyStatsCMDRunner implements CommandLineRunner {

    private MongoTemplate mongoTemplate;
    private DailyReportUrlGenerator dailyReportUrlGenerator;


    @Autowired
    public DailyStatsCMDRunner(MongoTemplate mongoTemplate, DailyReportUrlGenerator dailyReportUrlGenerator) {
        this.mongoTemplate = mongoTemplate;
        this.dailyReportUrlGenerator = dailyReportUrlGenerator;
    }

    @Override
    public void run(String... args) throws Exception {

        this.mongoTemplate.dropCollection(Stats.class);
        InputStream input = new URL(this.dailyReportUrlGenerator.getValidUrl()).openStream();
        Reader reader = new InputStreamReader(input);
        CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                .withType(Stats.class).withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Stats> statsList = csvToBean.parse();
        mongoTemplate.insertAll(statsList);
        reader.close();
    }
}
