package com.maotion.covid19api.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Stats {
    private String provinceOrState;
    private String countryOrRegion;
    private LocalDate lastUpdated;
    private Long confirmed;
    private Long deaths;
    private Long recovered;
    private Double latitude;
    private Double longitude;
    private List<News> news = new ArrayList<>();

    public Stats(String provinceOrState, String countryOrRegion, LocalDate lastUpdated, Long confirmed, Long deaths, Long recovered, Double latitude, Double longitude, List<News> news) {
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

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
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
