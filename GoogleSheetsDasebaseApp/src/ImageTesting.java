import java.io.IOException;
import java.util.List;

public class ImageTesting {
    public static void main (String[] args) throws IOException {
        Webscraper webscraper = new Webscraper("","");
        List<Object> list = webscraper.getInfo("https://www.allrecipes.com/recipe/23376/cinnamon-swirl-bread/");
        System.out.println(list);
    }
}
