package com.maotion.covid19api.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TrackerControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Test
    public void testReportCase() throws Exception {
        String uri = "/reportcase";
        String reportJson = "{" +
                "\"provinceOrState\": \"Utopia\"," +
                "\"countryOrRegion\": \"Atlantis\"," +
                "\"lastUpdated\": \"2020-03-04T12:53:03\"," +
                "\"confirmed\": 332," +
                "\"deaths\": 12," +
                "\"recovered\": 20," +
                "\"latitude\": 30.9756," +
                "\"longitude\": 139.638," +
                "\"news\": [" +
                "        {" +
                "            \"source\": \"ABC News\"," +
                "            \"title\": \"there is a new case.\"" +
                "        }," +
                "        {" +
                "            \"source\": \"Utopian Herald\"," +
                "            \"title\": \"it has just begun!\"" +
                "        }" +
                "]" +
                "}";

        String expectResContent = "{provinceOrState: Utopia, countryOrRegion: Atlantis, " +
                "lastUpdated: 2020-03-04, confirmed: 332, deaths:12, recovered: 20, " +
                "latitude:30.9756, longitude: 139.638}";

        RequestTestTemplate.testMvcRequest(webApplicationContext,uri,reportJson,
                200,expectResContent,false);
    }

}