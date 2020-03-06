package com.maotion.covid19api.entities;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Data
@Document(collection = "stats")
public class Stats {

    @Id
    private String id;
    private String provinceOrState;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String countryOrRegion;
    private String lastUpdated;
    private Long confirmed;
    private Long deaths;
    private Long recovered;
    private Double latitude;
    private Double longitude;
    private List<News> news = new ArrayList<>();

    public Stats(){}

    @PersistenceConstructor
    public Stats(String provinceOrState, String countryOrRegion, String lastUpdated, Long confirmed, Long deaths, Long recovered, Double latitude, Double longitude, List<News> news) {
        this.provinceOrState = provinceOrState;
        this.countryOrRegion = countryOrRegion;
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

    public String getProvinceOrState() {
        return provinceOrState;
    }

    public void setProvinceOrState(String provinceOrState) {
        this.provinceOrState = provinceOrState;
    }

    public String getCountryOrRegion() {
        return countryOrRegion;
    }

    public void setCountryOrRegion(String countryOrRegion) {
        this.countryOrRegion = countryOrRegion;
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
