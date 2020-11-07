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
}
