package com.maotion.covid19api.controllers;

import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class RequestTestTemplate {


    public static void testMvcRequest(WebApplicationContext context, String uri, String postContent,
                                      int expectedStatus, List<String> expectedContentKeywords) throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MvcResult mvcResult;
        if (postContent != null) {
            mvcResult = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(postContent)).andReturn();
        } else {
            mvcResult = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();
        }
        MockHttpServletResponse response = mvcResult.getResponse();
        int responseStatus = response.getStatus();
        String responseContent = response.getContentAsString();
        assertEquals(expectedStatus, responseStatus);
        if (expectedContentKeywords != null) {
            expectedContentKeywords.forEach(keyword ->
                    assertTrue(responseContent.contains(keyword))
            );
        }
    }

    public static void testMvcRequest(WebApplicationContext context, String uri, String postContent,
                                      int expectedStatus, String expectedContentJson, boolean strictJsonAssert) throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MvcResult mvcResult;
        if (postContent != null) {
            mvcResult = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(postContent)).andReturn();
        } else {
            mvcResult = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();
        }
        MockHttpServletResponse response = mvcResult.getResponse();
        int responseStatus = response.getStatus();
        String responseContent = response.getContentAsString();
        assertEquals(expectedStatus, responseStatus);
        if (expectedContentJson != null) {
            JSONAssert.assertEquals(expectedContentJson, responseContent, strictJsonAssert);
        }
    }
}
