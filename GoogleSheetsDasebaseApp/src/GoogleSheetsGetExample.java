import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class GoogleSheetsGetExample {
    public static void main (String[] args) throws IOException, GeneralSecurityException {
        GoogleSheetsService googleSheetsService = new GoogleSheetsService("1V1q8fhCVtTkACBJr5W88UKs-MgWwkByXT74dyyDbRt0");
        List<DataFilter> dataFilters = new ArrayList<>();
        DataFilter dataFilter = new DataFilter();
        GridRange gridRange = new GridRange();
        gridRange.setStartRowIndex(0);
        gridRange.setEndRowIndex(10);
        dataFilter.setGridRange(gridRange);
        dataFilters.add(dataFilter);
        BatchGetValuesByDataFilterResponse response = googleSheetsService.batchGet(dataFilters);
        List<List<Object>> recipes = response.getValueRanges().get(0).getValueRange().getValues();
        System.out.println(recipes.get(1));

    }
}
