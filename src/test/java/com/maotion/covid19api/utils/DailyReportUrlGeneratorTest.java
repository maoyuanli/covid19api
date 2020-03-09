package com.maotion.covid19api.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
class DailyReportUrlGeneratorTest {

    @Spy
    private DailyReportUrlGenerator dailyReportUrlGenerator;

    private LocalDate fakeCurDate;
    private String baseUrl = DailyReportUrlGenerator.baseUrl;
    private String curDateUrl = String.format(baseUrl, "03-01-2020");
    private String priorDateUrl = String.format(baseUrl, "02-29-2020");

    @BeforeEach
    public void setFakeToday() {
        fakeCurDate = LocalDate.of(2020, 3, 1);
    }

    @Test
    public void whenTodayAvailable() throws IOException {
        doReturn(fakeCurDate).when(dailyReportUrlGenerator).getToday();
        assertEquals(curDateUrl, dailyReportUrlGenerator.getValidUrl());
    }

    @Test
    public void whenTodayNotAvailable() throws IOException {
        doReturn(fakeCurDate).when(dailyReportUrlGenerator).getToday();
        doReturn(false).when(dailyReportUrlGenerator).csvAvailable(curDateUrl);
        assertEquals(priorDateUrl, dailyReportUrlGenerator.getValidUrl());
    }

}