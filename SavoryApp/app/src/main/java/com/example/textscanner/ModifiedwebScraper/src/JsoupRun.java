package com.example.textscanner.ModifiedwebScraper.src;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textscanner.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class JsoupRun extends AppCompatActivity {

    public List<String> run(EditText edit) throws IOException {
        String baseUrl = baseUrlInput(edit);
        List<String> linksList = new ArrayList<>();

        //System.out.println(baseUrl);

        String pageUrl = baseUrl;
        int pageNum = 2;
        int i = 0;
        while (isValid(pageUrl,i) && pageNum < 3) {
            Elements recipeLinks = findUrls(pageUrl, "h3.fixed-recipe-card__h3");
            pageUrl = baseUrl + "&page=" + pageNum;
            pageNum++;
            i++;
            linksList = printLinks(recipeLinks);
            //printLinks(recipeLinks); //Debuging
        }
        return linksList;
    }

    public static Elements findUrls(String mainUrl, String recipesHeader) throws IOException {
        Document doc = Jsoup.connect(mainUrl).get();
        Elements recipes = doc.select(recipesHeader);
        Elements links = recipes.select("a[href]");
        return links;
    }

    public static boolean isValid(String url, int i) {
        if( i < 1){
            /* Try creating a valid URL */
            try {
                new URL(url).toURI();
                return true;
            }
            // If there was an Exception
            // while creating URL object
            catch (Exception e) {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public static List<String> printLinks(Elements links) throws IOException {
        List<String> linksList = new ArrayList<>();
        int count = 1;
        for (Element link : links) {
            //System.out.println(count + ": " + link.attr("abs:href"));
            //getInfo(link.attr("abs:href"));
            linksList.add(link.attr("abs:href"));
            count++;
        }
        return linksList;
    }


    public String baseUrlInput(EditText edit){
        String recipesList = edit.getText().toString();

        StringBuilder result = new StringBuilder();
        String beginning = "https://www.allrecipes.com/search/results/?wt=";
        String filler = "%20";
        String ending = "&sort=re";

        result.append(beginning);

        for (int i = 0; i < recipesList.length(); i++) {
            if (Character.isWhitespace(recipesList.charAt(i))) {
                result.append(filler);
            } else {
                result.append(recipesList.charAt(i));
            }
        }
        result.append(ending);
        return result.toString();
    }


    public List<String> getInfo(String url) throws IOException {
        List<String> infoList = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();
        Elements base = doc.select("div");
        String Title = base.select("h1.headline.heading-content").text();
        String Summary = base.select("p").text();
        String info = base.select("section.recipe-meta-container.two-subcol-content.clearfix").text();
        String Ingredients = base.select("ul.ingredients-section").text();
        String Directions = base.select("ul.instructions-section").text();
        String imageUrl = "";
        try {
            imageUrl = doc.select("div.image-container").select("img").get(0).absUrl("src");
        } catch(IndexOutOfBoundsException e) {
            System.out.println("No image");
        }

        infoList.add(Title);
        infoList.add(Summary);
        infoList.add(info);
        infoList.add(Ingredients);
        infoList.add(Directions);
        infoList.add(imageUrl);


        //System.out.println("Title:" + Title);
        //System.out.println("Summary:" + Summary);
        //System.out.println("info:" + info);
        //System.out.println("Ingredients:" + Ingredients);
        //System.out.println("Directions:" + Directions);

        return infoList;


    }
}
