import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class scrappingThePage {
    public static void main(String[] args) throws IOException {
        String Url = ("https://www.allrecipes.com/recipe/238034/chef-johns-strawberry-ice-cream/");
        Document doc = Jsoup.connect(Url).get();
        Elements base = doc.select("div");
        String Title = base.select("h1.headline.heading-content").text();
        String Summary = base.select("p").text();
        String info = base.select("section.recipe-meta-container.two-subcol-content.clearfix").text();
        String Ingredients = base.select("ul.ingredients-section").text();
        String Directions = base.select("ul.instructions-section").text();

        System.out.println("Title:"+Title);
        System.out.println("Summary:"+Summary);
        System.out.println("info:"+info);
        System.out.println("Ingredients:"+Ingredients);
        System.out.println("Directions:"+Directions);
    }




}
