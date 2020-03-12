package com.maotion.covid19api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TrackerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setMockMvc() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testReportCase() throws Exception {
        String url = "/reportcase";
        String reportJson = "{" +
                "\"province\": \"Utopia\"," +
                "\"country\": \"Atlantis\"," +
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

        String expectResContent = "{province: Utopia, country: Atlantis, " +
                "lastUpdated: \"2020-03-04T12:53:03\", confirmed: 332, deaths:12, recovered: 20, " +
                "latitude:30.9756, longitude: 139.638}";

        RequestTestTemplate.testMvcRequest(webApplicationContext, url, reportJson,
                200, expectResContent, false);
    }

    @Test
    public void testGetAllCase() throws Exception {
        String url = "/getallcase";
        List<String> expectResKeywords = Arrays.asList("province", "country",
                "lastUpdated", "confirmed", "deaths", "recovered", "latitude", "longitude");
        RequestTestTemplate.testMvcRequest(webApplicationContext, url, null,
                200, expectResKeywords);
    }

    @Test
    public void testFindByCountry() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/findbycountry/China"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200))
                .andExpect(mvcResult -> assertThat(mvcResult.getResponse().getContentAsString())
                        .contains("Hubei", "Beijing", "Shanghai", "confirmed", "lastUpdated"));
    }

    @Test
    public void testDeleteByCountry() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/deletecase/?country=Italy"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> assertThat(mvcResult.getResponse().getContentAsString())
                        .contains("id", "province", "country", "confirmed", "lastUpdated"));
    }
}