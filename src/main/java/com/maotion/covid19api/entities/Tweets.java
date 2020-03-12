package com.maotion.covid19api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "tweets")
public class Tweets {
    @Id
    private String id;
    private String createdAt;
    private String text;
    private String userSreenName;

    @JsonProperty("user")
    private void unpackTweets(Map<String, String> user) {
        this.userSreenName = (String) user.get("screen_name");
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserSreenName() {
        return userSreenName;
    }

    public void setUserSreenName(String userSreenName) {
        this.userSreenName = userSreenName;
    }

}
