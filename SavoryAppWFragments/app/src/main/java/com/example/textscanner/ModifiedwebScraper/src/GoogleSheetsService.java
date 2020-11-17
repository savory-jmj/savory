package com.example.textscanner.ModifiedwebScraper.src;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class GoogleSheetsService {
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl() throws IOException, JSONException {
        String url = "https://spreadsheets.google.com/feeds/list/1V1q8fhCVtTkACBJr5W88UKs-MgWwkByXT74dyyDbRt0/default/public/values?alt=json";
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject googleSheet = new JSONObject(jsonText);
            JSONObject recipes = (JSONObject) googleSheet.get("feed");
            return recipes;
        } finally {
            is.close();
        }
    }

    public void getRecipes() throws IOException, JSONException {
        //plz return a full list of List<Arraylist<String>>
        //you might wanna keep only like twenty entires for the data base
        JSONObject json = readJsonFromUrl();
        JSONArray recipes = (JSONArray) json.get("entry");
        String title = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$title")).get("$t");
        String url = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$url")).get("$t");
        String summary = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$summary")).get("$t");
        String info = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$info")).get("$t");
        String ingredients = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$ingredients")).get("$t");
        String directions = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$directions")).get("$t");
        String imageUrl = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$imageurl")).get("$t");
    }
}
