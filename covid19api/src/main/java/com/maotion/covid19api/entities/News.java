package com.maotion.covid19api.entities;

public class News {
    private String source;
    private String headline;

    public News(String source, String headline) {
        this.source = source;
        this.headline = headline;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }
}
