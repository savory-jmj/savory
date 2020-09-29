import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupRun {
    public static void main (String[] args) throws IOException {
        //Elements recipeLinks = findUrls("https://www.allrecipes.com/search/results/?wt=spaghetti&sort=re", "h3.fixed-recipe-card__h3");
        Elements recipeLinks = findUrls("https://www.allrecipes.com/recipes/78/breakfast-and-brunch/", "a.recipeCard__titleLink");
        printLinks(recipeLinks);
    }

    public static Elements findUrls(String mainUrl, String recipesHeader) throws IOException {
        Document doc = Jsoup.connect(mainUrl).timeout(0).maxBodySize(0).get();
        Elements recipes = doc.select(recipesHeader);
        Elements links = recipes.select("a[href]");
        return links;
    }
    
    public static void printLinks(Elements links) {
        int count = 1;
        for (Element link : links) {
            System.out.println(count + ": " + link.attr("abs:href"));
            count++;
        }
    }
}
