package com.maotion.covid19api.utils;

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
        File targetFile = new File(targetFilePath);
        Files.copy(input, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        input.close();
    }
}
