package utils;

import java.util.*;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import org.testng.Assert;
import reporting.ExtentLogger;
import reporting.ExtentManager;

public class AssertionUtils {
    public static void assertExpectedValuesWithJsonPath(Response response,
                                                        Map<String, Object> expectedValuesMap) {

        List<AssertionKeys> actualValueMap = new ArrayList<>();
        actualValueMap.add(new AssertionKeys("JSON_PATH", "EXPECTED_VALUE", "ACTUAL_VALUE", "RESULT"));
        boolean allMatch = true;

        Set<String> jsonPaths = expectedValuesMap.keySet();
        for (String jsonPath : jsonPaths) {
            Optional<Object> actualValue = Optional.ofNullable(response.jsonPath().get(jsonPath));
            if (actualValue.isPresent()) {
                Object value = actualValue.get();
                if (value.equals(expectedValuesMap.get(jsonPath))) {
                    actualValueMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "MATCHED"));
                } else {
                    allMatch = false;
                    actualValueMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "NOT_MATCHED"));
                }
            } else {
                actualValueMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), "VALUE_NOT_FOUND", "MATCHED"));
            }
        }
        if (allMatch)
            ExtentLogger.pass("All assertions are passed");
        else
            ExtentLogger.logFailureDetails("All assertions are not passed");

        String[][] finalAssertionMap = actualValueMap.stream().map(assertions -> new String[]{assertions.getJsonPath(),
                        String.valueOf(assertions.getExpectedValue()), String.valueOf(assertions.getActualValue()), assertions.getResult()})
                .toArray(String[][]::new);
        ExtentManager.getExtentTest().info(MarkupHelper.createTable(finalAssertionMap));
    }

    public static void assertKeyAndValue(String expected, Object actual) {

        if (actual.equals(expected)) {
            ExtentLogger.logInfo(MarkupHelper.createLabel("Assertion Passed:", ExtentColor.GREEN));
        } else {
            ExtentLogger.logInfo(MarkupHelper.createLabel("Failed", ExtentColor.RED));
            ExtentLogger.logInfo("Assertion Failed: ACTUAL_VALUE is " + actual + " but EXPECTED_VALUE is " + expected);
        }
    }

    public static void assertListValuesOnIndex(Response response, int index, String fieldName, List<Object> expectedValues) {

        List<Object> actualValue = new ArrayList<>();

        // Parse the JSON response
        JsonArray jsonArray = JsonParser.parseString(response.getBody().asString()).getAsJsonArray();

        // Extract the  field values from the object based on index in the response array
        if (jsonArray.size() > 0) {
            JsonElement firstObject = jsonArray.get(index);
            JsonArray fieldArray = firstObject.getAsJsonObject().getAsJsonArray(fieldName);
            for (JsonElement element : fieldArray) {
                actualValue.add(element.getAsString());
            }
            if (actualValue.equals(expectedValues)) {
                ExtentLogger.pass("All field values matches with expectedValues");
            } else {
                ExtentLogger.logFailureDetails("Field values didn't match with expectedValues");
            }
        } else {
            ExtentLogger.logInfo("No data found in the response for field " + fieldName);
        }

    }
}


