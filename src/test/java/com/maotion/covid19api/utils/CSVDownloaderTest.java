package com.maotion.covid19api.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class CSVDownloaderTest {

    @Test
    public void testDownloadCSV(@TempDir Path tempDir) throws IOException {
        String baseUrl = SourceUrlGenerator.baseUrl;
        String testingUrl = String.format(baseUrl, "02-29-2020");
        Path downloadedCSV = tempDir.resolve("test-daily-data.csv");
        CSVDownloader downloader = new CSVDownloader();
        downloader.downloadCSV(testingUrl, downloadedCSV.toString());

        assertThat(Files.exists(downloadedCSV)).isTrue();
        assertThat(Files.readAllLines(downloadedCSV).toString()).contains("2020-02-29T", "China", "Japan", "US");
    }
}