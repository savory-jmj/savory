import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class JsoupRun {
    public static void main (String[] args) throws IOException {
        //Elements recipeLinks = findUrls("https://www.allrecipes.com/search/results/?wt=spaghetti&sort=re", "h3.fixed-recipe-card__h3");
        //Elements recipeLinks = findUrlas("https://www.allrecipes.com/recipes/78/breakfast-and-brunch/", "recipeCard__titleLinka");
        String baseUrl = baseUrlInput();

        System.out.println(baseUrl);

        String pageUrl = baseUrl;
        int pageNum = 2;
        while (isValid(pageUrl)&& pageNum < 3) {
            Elements recipeLinks = findUrls(pageUrl, "h3.fixed-recipe-card__h3");
            pageUrl = baseUrl + "&page=" + pageNum;
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
    
    public static void printLinks(Elements links) throws IOException{
        int count = 1;
        for (Element link : links) {
            System.out.println(count + ": " + link.attr("abs:href"));
            getInfo(link.attr("abs:href"));
            count++;
        }
    }

    public static String baseUrlInput(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Search input:");
        String input= sc.nextLine();
        sc.close();

        StringBuilder result = new StringBuilder();
        String beginning = "https://www.allrecipes.com/search/results/?wt=";
        String filler = "%20";
        String ending = "&sort=re";

        result.append(beginning);

        for(int i = 0; i< input.length(); i++){
            if(Character.isWhitespace(input.charAt(i))){
                result.append(filler);
            }
            else{
                result.append(input.charAt(i));
            }
        }
        result.append(ending);
        return result.toString();
    }
    public static void getInfo(String url) throws IOException{
        Document doc = Jsoup.connect(url).get();
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

