package com.maotion.covid19api.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class TweetsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testGetAllTweets() throws Exception {
        String url = "/getalltweets";
        List<String> expectResKeywords = Arrays.asList("userName", "createdAt", "text");
        RequestTestTemplate.testMvcRequest(webApplicationContext, url, null,
                200, expectResKeywords);
    }
}