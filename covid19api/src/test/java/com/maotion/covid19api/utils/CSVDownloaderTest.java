package com.maotion.covid19api.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class CSVDownloaderTest {
    @Test
    public void testDownloadCSV() throws IOException {
        CSVDownloader downloader = new CSVDownloader();
        downloader.downloadCSV("src/main/resources/test-daily-data.csv");
    }
}