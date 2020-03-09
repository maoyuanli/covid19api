package com.maotion.covid19api.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DailyReportUrlGenerator {

    public static String baseUrl =
            "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/%s.csv";

    public String getValidUrl() throws IOException {
        LocalDate curDate = getToday();
        String csvUrl = finishUrlWithDate(curDate);
        while (!csvAvailable(csvUrl)) {
            curDate = curDate.minusDays(1);
            csvUrl = finishUrlWithDate(curDate);
        }
        return csvUrl;
    }

    private String finishUrlWithDate(LocalDate date) {
        String dateStrPattern = "MM-dd-yyyy";
        String todayStr = formatLocalDate(date, dateStrPattern);
        return String.format(baseUrl, todayStr);
    }

    private String formatLocalDate(LocalDate localDate, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(localDate);
    }

    public LocalDate getToday() {
        return LocalDate.now();
    }

    public boolean csvAvailable(String csvUrl) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(new HttpGet(csvUrl));
        int statusCode = response.getStatusLine().getStatusCode();
        return statusCode == HttpStatus.SC_OK;
    }
}
