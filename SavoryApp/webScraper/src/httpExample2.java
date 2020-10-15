import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class httpExample2 {
    public static void main(String args[]) throws IOException {
        URL url = new URL("https://sheets.googleapis.com/v4/spreadsheets/1V1q8fhCVtTkACBJr5W88UKs-MgWwkByXT74dyyDbRt0/values/A1:append");
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST"); // PUT is another valid option
        http.setDoOutput(true);

        byte[] out = "{\"values\":[[\"hello\",23]]}" .getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        http.connect();
        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
    }
}
