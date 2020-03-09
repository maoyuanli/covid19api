package com.maotion.covid19api.entities;


import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ts_confirmed")
public class TimeSeriesConfirmed {

    @Id
    private String id;

    @CsvBindByPosition(position = 0)
    private String province;

    @CsvBindByPosition(position = 1)
    private String country;

    @CsvBindAndJoinByName(column = "\\d+/\\d+/\\d+", elementType = String.class)
    private MultiValuedMap<String, Long> dailyUpdate;

}
