import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class GoogleSheetsGetExample {
    public static void main (String[] args) throws IOException, GeneralSecurityException {
        // Setting up the google sheets api with the Savory google sheets id
        GoogleSheetsService googleSheetsService = new GoogleSheetsService("1V1q8fhCVtTkACBJr5W88UKs-MgWwkByXT74dyyDbRt0");

        // setting a datafilter to set the number of recipes we want to pull from the google sheets
        List<DataFilter> dataFilters = new ArrayList<>();
        DataFilter dataFilter = new DataFilter();
        GridRange gridRange = new GridRange();
        // We are grabbing rows 0 to 9
        gridRange.setStartRowIndex(0);
        gridRange.setEndRowIndex(10);
        dataFilter.setGridRange(gridRange);
        dataFilters.add(dataFilter);

        // Using the google sheets batch get api to request for rows 0 to 9.
        BatchGetValuesByDataFilterResponse response = googleSheetsService.batchGet(dataFilters);
        List<List<Object>> recipes = response.getValueRanges().get(0).getValueRange().getValues();
        for (int i = 0; i < 10; i++) {
            System.out.println(recipes.get(i));
        }
    }
}
