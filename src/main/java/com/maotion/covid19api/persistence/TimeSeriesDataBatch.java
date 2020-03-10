package com.maotion.covid19api.persistence;


import com.maotion.covid19api.entities.TimeSeriesConfirmed;
import com.maotion.covid19api.utils.TimeSeriesDataRetriever;
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

@EnableBatchProcessing
@Configuration
public class TimeSeriesDataBatch {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private MongoTemplate mongoTemplate;
    private TimeSeriesDataRetriever timeSeriesDataRetriever;

    @Autowired
    public TimeSeriesDataBatch(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                               MongoTemplate mongoTemplate, TimeSeriesDataRetriever timeSeriesDataRetriever) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.mongoTemplate = mongoTemplate;
        this.timeSeriesDataRetriever = timeSeriesDataRetriever;
    }

    @Bean
    public Job readCSVFile() throws IOException {
        this.mongoTemplate.dropCollection("ts_confirmed");
        return jobBuilderFactory.get("readTimeSeriesConfirmed").incrementer(new RunIdIncrementer()).start(step1())
                .build();
    }

    @Bean
    public Step step1() throws IOException {
        return stepBuilderFactory.get("step1").<TimeSeriesConfirmed, TimeSeriesConfirmed>chunk(10).reader(reader())
                .writer(writer()).build();
    }

    @Bean
    public FlatFileItemReader<TimeSeriesConfirmed> reader() throws IOException {
        FlatFileItemReader<TimeSeriesConfirmed> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new UrlResource(timeSeriesDataRetriever.TIME_SERIES_CONFIRMED));
        reader.setLineMapper(new DefaultLineMapper<TimeSeriesConfirmed>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("province", "country", "prior5Day", "prior4Day", "prior3Day", "prior2Day", "prior1Day");
                setIncludedFields(timeSeriesDataRetriever.locatePriorDaysColumns(5));
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