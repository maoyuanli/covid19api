package com.maotion.covid19api.batch;


import com.maotion.covid19api.entities.TimeSeriesConfirmed;
import com.maotion.covid19api.utils.CSVColumnCounter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.net.MalformedURLException;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private MongoTemplate mongoTemplate;
    private CSVColumnCounter csvColumnCounter;

    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                       MongoTemplate mongoTemplate, CSVColumnCounter csvColumnCounter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.mongoTemplate = mongoTemplate;
        this.csvColumnCounter = csvColumnCounter;
    }

    @Bean
    public Job readCSVFile() throws IOException {
        this.mongoTemplate.dropCollection("ts_confirmed");
        return jobBuilderFactory.get("readCSVFile").incrementer(new RunIdIncrementer()).start(step1())
                .build();
    }

    @Bean
    public Step step1() throws MalformedURLException {
        return stepBuilderFactory.get("step1").<TimeSeriesConfirmed, TimeSeriesConfirmed>chunk(10).reader(reader())
                .writer(writer()).build();
    }

    @Bean
    public FlatFileItemReader<TimeSeriesConfirmed> reader() throws MalformedURLException {
        FlatFileItemReader<TimeSeriesConfirmed> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new UrlResource(csvColumnCounter.TIME_SERIES_CONFIRMED));
        reader.setLineMapper(new DefaultLineMapper<TimeSeriesConfirmed>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("province", "country", "dailyUpdates");
                setIncludedFields(0, 1, 4);
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<TimeSeriesConfirmed>() {{
                setTargetType(TimeSeriesConfirmed.class);
            }});
        }});
        return reader;
    }

    @Bean
    public MongoItemWriter<TimeSeriesConfirmed> writer() {
        MongoItemWriter<TimeSeriesConfirmed> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("ts_confirmed");
        return writer;
    }
}