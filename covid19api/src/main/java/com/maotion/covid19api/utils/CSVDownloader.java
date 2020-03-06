package com.maotion.covid19api.utils;

import com.maotion.covid19api.entities.Stats;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class CSVDownloader {


    private final String CSV_URL =
            "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/03-05-2020.csv";


    public void downloadCSV(String targetFilePath) throws IOException {

        InputStream input = new URL(CSV_URL).openStream();
//        File targetFile = new File("src/main/resources/daily-data.csv");
        File targetFile = new File(targetFilePath);
        Files.copy(input, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        IOUtils.closeQuietly(input);
    }
}
