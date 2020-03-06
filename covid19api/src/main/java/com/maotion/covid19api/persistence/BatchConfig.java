package com.maotion.covid19api.persistence;

import com.maotion.covid19api.entities.Case;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public Job readCSVFile() {
        return jobBuilderFactory.get("readCSVFile").incrementer(new RunIdIncrementer()).start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Case,Case>chunk(10).reader(reader())
                .writer(writer()).build();
    }

    @Bean
    public FlatFileItemReader<Case> reader() {
        FlatFileItemReader<Case> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("daily-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Case>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"provinceOrState", "countryOrRegion", "lastUpdated",
                        "confirmed", "deaths", "recovered", "latitude", "longitude"});

            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Case>() {{
                setTargetType(Case.class);
            }});
        }});
        return reader;
    }

    @Bean
    public MongoItemWriter<Case> writer() {
        MongoItemWriter<Case> writer = new MongoItemWriter<Case>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("stats");
        return writer;
    }
}
