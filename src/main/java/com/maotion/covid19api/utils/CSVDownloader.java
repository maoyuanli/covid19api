package com.maotion.covid19api.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
public class CSVDownloader {

    public void downloadCSV(String sourceUrl, String targetFilePath) throws IOException {

            InputStream input = new URL(sourceUrl).openStream();
//        File targetFile = new File("src/main/resources/daily-data.csv");
            File targetFile = new File(targetFilePath);
            Files.copy(input, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            input.close();
    }
}
