import java.util.*;

public class searchFeature {
    public static void main(String[] args) {
        System.out.println(baseUrlInput());
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
}
