package com.example.textscanner.ModifiedwebScraper.src;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class GoogleSheetsService {
    
    // readAll() takes in a reader and concatenates a string to return.
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl() throws IOException, JSONException {
        // url of public Savary database
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

    public List<ArrayList<String>> getRecipes(int index) throws IOException, JSONException {
        List<ArrayList<String>> recipesList = new ArrayList<ArrayList<String>>();

        JSONObject json = new JSONObject();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            json = readJsonFromUrl();
        }

        JSONArray recipes = (JSONArray) json.get("entry");
        for (int i = index; i < index+20; i++) {
            String title = (String) ((JSONObject) ((JSONObject) recipes.get(i)).get("gsx$title")).get("$t");
            String url = (String) ((JSONObject) ((JSONObject) recipes.get(i)).get("gsx$url")).get("$t");
            String summary = (String) ((JSONObject) ((JSONObject) recipes.get(i)).get("gsx$summary")).get("$t");
            String info = (String) ((JSONObject) ((JSONObject) recipes.get(i)).get("gsx$info")).get("$t");
            String ingredients = (String) ((JSONObject) ((JSONObject) recipes.get(i)).get("gsx$ingredients")).get("$t");
            String directions = (String) ((JSONObject) ((JSONObject) recipes.get(i)).get("gsx$directions")).get("$t");
            String imageUrl = (String) ((JSONObject) ((JSONObject) recipes.get(i)).get("gsx$imageurl")).get("$t");

            ArrayList<String> recipe = new ArrayList<>();
            recipe.add(title);
            recipe.add(summary);
            recipe.add(info);
            recipe.add(ingredients);
            recipe.add(directions);
            recipe.add(imageUrl);
            recipesList.add(recipe);
        }
        return recipesList;
    }
}
