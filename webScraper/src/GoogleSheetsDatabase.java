import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class GoogleSheetsDatabase {
    public static void main (String[] args) throws IOException, GeneralSecurityException {
        String breakfastLink = "https://www.allrecipes.com/recipes/78/breakfast-and-brunch/";
        String lunchLink = "https://www.allrecipes.com/recipes/17561/lunch/";
        String dinnerLink = "https://www.allrecipes.com/recipes/17562/dinner/";
        GoogleSheetsAppend googleSheetsAppend = new GoogleSheetsAppend("1V1q8fhCVtTkACBJr5W88UKs-MgWwkByXT74dyyDbRt0", "A1", "RAW", "INSERT_ROWS");
        Webscraper webscraper = new Webscraper(dinnerLink, "a.recipeCard__titleLink");
        List<List<Object>> list = webscraper.visitPage();
        googleSheetsAppend.appendRow(list);
    }
}
