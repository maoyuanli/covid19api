package com.maotion.covid19api.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Reader implements ItemReader<java.io.Reader> {
    private final static String TIME_SERIES_CONFIRMED =
            "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    @Override
    public java.io.Reader read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        InputStream input = new URL(TIME_SERIES_CONFIRMED).openStream();
        java.io.Reader reader = new InputStreamReader(input);
        return reader;
    }
}
