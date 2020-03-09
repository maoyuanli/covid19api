package com.maotion.covid19api.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class TimeSeriesDataRetrieverTest {

    @Test
    public void testCSVHeader() throws IOException {
        TimeSeriesDataRetriever counter = new TimeSeriesDataRetriever();
        String[] headersArray = counter.getHeaders();
        assertThat(headersArray.length).isGreaterThan(50);
        assertThat(headersArray).contains("Province/State", "Country/Region", "1/22/20", "3/8/20");
    }

}