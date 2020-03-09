package com.maotion.covid19api.utils;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@Component
public class CSVColumnCounter {

    public final String TIME_SERIES_CONFIRMED =
            "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";


    public String[] getHeaders() throws IOException {
        InputStream input = new URL(TIME_SERIES_CONFIRMED).openStream();
        CSVReader csvReader = new CSVReader(new InputStreamReader(input));
        String[] headers = csvReader.readNext();
        return headers;
    }
}
