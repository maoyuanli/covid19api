package com.maotion.covid19api.entities;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ts_confirmed")
public class TimeSeriesConfirmed {

    @Id
    private String id;
    private String province;
    private String country;
    private String dailyUpdates;

}
