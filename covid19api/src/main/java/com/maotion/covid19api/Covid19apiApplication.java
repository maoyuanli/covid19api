package com.maotion.covid19api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Covid19apiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Covid19apiApplication.class, args);
    }

}
