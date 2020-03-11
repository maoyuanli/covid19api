package com.maotion.covid19api.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class TokenFetcher {

    private String tokenFile;

    public TokenFetcher(String tokenFile) {
        this.tokenFile = tokenFile;
    }

    public String fetchToken(String tokenKey) {
        URL tokenPath = Resources.getResource(tokenFile);
        Map<String, String> tokenKeyValMap = new HashMap<>();
        try {
            String tokenContent = Resources.toString(tokenPath, Charsets.UTF_8);
            Gson gson = new Gson();
            tokenKeyValMap = gson.fromJson(tokenContent, HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tokenKeyValMap.get(tokenKey);
    }
}
