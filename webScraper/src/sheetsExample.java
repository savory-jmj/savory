import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class sheetsExample {
    public static void main(String args[]) throws IOException, GeneralSecurityException {
        // The ID of the spreadsheet to update.
        String spreadsheetId = "1V1q8fhCVtTkACBJr5W88UKs-MgWwkByXT74dyyDbRt0"; // TODO: Update placeholder value.

        // The A1 notation of a range to search for a logical table of data.
        // Values will be appended after the last row of the table.
        String range = "A1"; // TODO: Update placeholder value.

        // How the input data should be interpreted.
        String valueInputOption = "RAW"; // TODO: Update placeholder value.

        // How the input data should be inserted.
        String insertDataOption = ""; // TODO: Update placeholder value.

        // TODO: Assign values to desired fields of `requestBody`:
        ValueRange requestBody = new ValueRange();
        List<List<Object>> x = Arrays.asList(Arrays.asList(1,2));
        requestBody = requestBody.setValues(x);

        Sheets sheetsService = createSheetsService();
        Sheets.Spreadsheets.Values.Append request =
                sheetsService.spreadsheets().values().append(spreadsheetId, range, requestBody);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        AppendValuesResponse response = request.execute();

        // TODO: Change code below to process the `response` object:
        System.out.println(response);
    }

    public static Sheets createSheetsService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        // TODO: Change placeholder below to generate authentication credentials. See
        // https://developers.google.com/sheets/quickstart/java#step_3_set_up_the_sample
        //
        // Authorize using one of the following scopes:
        //   "https://www.googleapis.com/auth/drive"
        //   "https://www.googleapis.com/auth/drive.file"
        //   "https://www.googleapis.com/auth/spreadsheets"
        HttpRequestInitializer credential = new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {

            }
        };

        return new Sheets.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Google-SheetsSample/0.1")
                .build();
    }
}