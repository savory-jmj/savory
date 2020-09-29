import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class JsoupRun {
    public static void main (String[] args) throws IOException {
        //Elements recipeLinks = findUrls("https://www.allrecipes.com/search/results/?wt=spaghetti&sort=re", "h3.fixed-recipe-card__h3");
        //Elements recipeLinks = findUrls("https://www.allrecipes.com/recipes/78/breakfast-and-brunch/", "a.recipeCard__titleLink");
        String baseUrl = "https://www.allrecipes.com/recipes/17561/lunch/";
        String pageUrl = baseUrl;
        int pageNum = 2;
        while (isValid(pageUrl)) {
            Elements recipeLinks = findUrls(pageUrl, "a.recipeCard__titleLink");
            pageUrl = baseUrl + "?page=" + pageNum;
            pageNum++;
            printLinks(recipeLinks);
        }

    }

    public static Elements findUrls(String mainUrl, String recipesHeader) throws IOException {
        Document doc = Jsoup.connect(mainUrl).get();
        Elements recipes = doc.select(recipesHeader);
        Elements links = recipes.select("a[href]");
        return links;
    }

    public static boolean isValid(String url)
    {
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
    
    public static void printLinks(Elements links) {
        int count = 1;
        for (Element link : links) {
            System.out.println(count + ": " + link.attr("abs:href"));
            count++;
        }
    }
}
