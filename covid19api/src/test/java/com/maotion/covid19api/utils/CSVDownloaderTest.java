package com.maotion.covid19api.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CSVDownloaderTest {
    String baseUrl = SourceUrlGenerator.baseUrl;
    String testingUrl = String.format(baseUrl, "02-29-2020");
//    private final String targetFilePath = "src/main/resources/test-daily-data.csv";

    @Test
    public void testDownloadCSV(@TempDir Path tempDir) throws IOException {

        Path downloadedCSV = tempDir.resolve("test-daily-data.csv");
        CSVDownloader downloader = new CSVDownloader();
        downloader.downloadCSV(testingUrl, downloadedCSV.toString());

        assertTrue(Files.exists(downloadedCSV));
        assertTrue(Files.readAllLines(downloadedCSV).contains("2020-02-29T"));
    }
}