import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Webscraper {
    private String baseUrl;
    private String pageUrl;
    private String recipeHeader;

    public Webscraper (String baseUrl, String recipeHeader) {
        this.baseUrl = baseUrl;
        this.pageUrl = baseUrl;
        this.recipeHeader = recipeHeader;
    }

    public List<List<Object>> visitPage() throws IOException {
        List<List<Object>> list = new ArrayList<>();
        int pageNum = 2;
        while (isValid(pageUrl) && pageNum < 6) {
            pageUrl = baseUrl + "?page=" + pageNum;
            Elements recipeLinks = findUrls(pageUrl, recipeHeader);
            pageNum++;
            list.addAll(visitLinks(recipeLinks));
        }
        return list;
    }

    public boolean isValid(String url)
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

    public List<List<Object>> visitLinks(Elements links) throws IOException{
        List<List<Object>> list = new ArrayList<>();
        for (Element link : links) {
            list.addAll(Arrays.asList(getInfo(link.attr("abs:href"))));
        }
        return list;
    }

    public Elements findUrls(String mainUrl, String recipesHeader) throws IOException {
        Document doc = Jsoup.connect(mainUrl).get();
        //Elements recipesContainer = doc.select("div.card__detailsContainer-left");
        Elements recipes = doc.select(recipesHeader);
        Elements links = recipes.select("a[href]");
        return links;
    }

    public List<Object> getInfo(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements base = doc.select("div");
        String Title = base.select("h1.headline.heading-content").text();
        String Summary = base.select("p").text();
        String info = base.select("section.recipe-meta-container.two-subcol-content.clearfix").text();
        String Ingredients = base.select("ul.ingredients-section").text();
        String Directions = base.select("ul.instructions-section").text();
        Directions = Directions.replaceAll("Advertisement", "");
        String imageUrl = "";
        try {
            imageUrl = doc.select("div.image-container").select("img").get(0).absUrl("src");
        } catch(IndexOutOfBoundsException e) {
            System.out.println("No image");
        }
        List<Object> list = Arrays.asList(Title, url, Summary, info, Ingredients, Directions, imageUrl);

        return list;
    }
}
