package com.maotion.covid19api.entities;

public class News {
    private String source;
    private String title;

    public News(String source, String title) {
        this.source = source;
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
