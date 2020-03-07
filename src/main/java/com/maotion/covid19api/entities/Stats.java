package com.maotion.covid19api.entities;


import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Data
@Document(collection = "stats")
public class Stats {

    @Id
    private String id;

    @CsvBindByPosition(position = 0)
    private String province;

    @CsvBindByPosition(position = 1)
    private String country;

    @CsvBindByPosition(position = 2)
    private String lastUpdated;

    @CsvBindByPosition(position = 3)
    private Long confirmed;

    @CsvBindByPosition(position = 4)
    private Long deaths;

    @CsvBindByPosition(position = 5)
    private Long recovered;

    @CsvBindByPosition(position = 6)
    private Double latitude;

    @CsvBindByPosition(position = 7)
    private Double longitude;

    private List<News> news = new ArrayList<>();

    public Stats() {
    }

    @PersistenceConstructor
    public Stats(String province, String country, String lastUpdated, Long confirmed, Long deaths, Long recovered, Double latitude, Double longitude, List<News> news) {
        this.province = province;
        this.country = country;
        this.lastUpdated = lastUpdated;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.latitude = latitude;
        this.longitude = longitude;
        if (news != null) {
            this.news = news;
        }
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Long confirmed) {
        this.confirmed = confirmed;
    }

    public Long getDeaths() {
        return deaths;
    }

    public void setDeaths(Long deaths) {
        this.deaths = deaths;
    }

    public Long getRecovered() {
        return recovered;
    }

    public void setRecovered(Long recovered) {
        this.recovered = recovered;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
