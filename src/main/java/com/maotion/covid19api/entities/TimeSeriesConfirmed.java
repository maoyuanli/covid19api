package com.maotion.covid19api.entities;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ts_confirmed")
public class TimeSeriesConfirmed {

    @Id
    private String id;
    private String province;
    private String country;
    private Long prior1Day;
    private Long prior2Day;
    private Long prior3Day;
    private Long prior4Day;
    private Long prior5Day;

    public TimeSeriesConfirmed() {
    }

    @PersistenceConstructor
    public TimeSeriesConfirmed(String province, String country, Long prior1Day, Long prior2Day, Long prior3Day, Long prior4Day, Long prior5Day) {
        this.province = province;
        this.country = country;
        this.prior1Day = prior1Day;
        this.prior2Day = prior2Day;
        this.prior3Day = prior3Day;
        this.prior4Day = prior4Day;
        this.prior5Day = prior5Day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getPrior1Day() {
        return prior1Day;
    }

    public void setPrior1Day(Long prior1Day) {
        this.prior1Day = prior1Day;
    }

    public Long getPrior2Day() {
        return prior2Day;
    }

    public void setPrior2Day(Long prior2Day) {
        this.prior2Day = prior2Day;
    }

    public Long getPrior3Day() {
        return prior3Day;
    }

    public void setPrior3Day(Long prior3Day) {
        this.prior3Day = prior3Day;
    }

    public Long getPrior4Day() {
        return prior4Day;
    }

    public void setPrior4Day(Long prior4Day) {
        this.prior4Day = prior4Day;
    }

    public Long getPrior5Day() {
        return prior5Day;
    }

    public void setPrior5Day(Long prior5Day) {
        this.prior5Day = prior5Day;
    }
}
