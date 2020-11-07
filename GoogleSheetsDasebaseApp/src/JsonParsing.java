import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonParsing {
    public static void main(String[] args) throws IOException {
        JSONObject json = readJsonFromUrl();
        JSONArray recipes = (JSONArray) json.get("entry");
        String title = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$title")).get("$t");
        String url = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$url")).get("$t");
        String summary = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$summary")).get("$t");
        String info = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$info")).get("$t");
        String ingredients = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$ingredients")).get("$t");
        String directions = (String) ((JSONObject) ((JSONObject) recipes.get(0)).get("gsx$directions")).get("$t");
        System.out.println(title);
        System.out.println(url);
        System.out.println(summary);
        System.out.println(info);
        System.out.println(ingredients);
        System.out.println(directions);
    }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl() throws IOException, JSONException {
        String url = "https://spreadsheets.google.com/feeds/list/1V1q8fhCVtTkACBJr5W88UKs-MgWwkByXT74dyyDbRt0/default/public/values?alt=json";
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            JSONObject json2 = (JSONObject) json.get("feed");
            return json2;
        } finally {
            is.close();
        }
    }
}
