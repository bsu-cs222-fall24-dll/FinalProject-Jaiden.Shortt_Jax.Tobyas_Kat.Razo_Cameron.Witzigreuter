package edu.bsu.cs;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;

/* JsonPath documentation states the following:
 ^ > When using JsonPath in java its [sic] important to know what type you expect in your result.
 * > JsonPath will automatically try to cast the result to the type expected by the invoker.
 * Because of this, unchecked casts are necessary to properly store data.
 *
 * Also, the IDE seems to think the suppression is redundant when it truly is not.
 * As such, the Redundant Suppression is, ironically, also suppressed.
 */
@SuppressWarnings({"unchecked", "RedundantSuppression"})

public class JsonReader {
    private final Object JSON;

    private JsonReader(String json) {
        JSON = Configuration.defaultConfiguration().jsonProvider().parse(json);
    }
    public static JsonReader createReader(String json) {
        return new JsonReader(json);
    }

    private Object singleScan(String keyWithPath) {
        return JsonPath.read(JSON, String.format("$.%s", keyWithPath));
    }
    private Object deepScan(String keyWithPath) {
        return JsonPath.read(JSON, String.format("$..%s", keyWithPath));
    }

    public String getValue(String keyToValueWithPath) {
        return (String) singleScan(keyToValueWithPath);
    }
    public List<String> getListOfValues(String keyToArrayWithPath) {
        return (List<String>) singleScan(keyToArrayWithPath);
    }
    public Map<String, String> getMap(String keyToMapWithPath) {
        return (Map<String, String>) singleScan(keyToMapWithPath);
    }
    public List<Map<String, String>> getListOfMaps(String keyToArrayWithPath) {
        return (List<Map<String, String>>) singleScan(keyToArrayWithPath);
    }

    public List<String> getAnyValuesAsList(String keyWithoutPath) {
        StringBuilder roughList = new StringBuilder(deepScan(keyWithoutPath).toString());

        roughList.delete(0, 1);
        roughList.delete(roughList.length() - 1, roughList.length());

        int indexOfQuote = roughList.indexOf("\"");
        while (indexOfQuote != -1) {
            roughList.delete(indexOfQuote, indexOfQuote + 1);
            indexOfQuote = roughList.indexOf("\"");
        }

        String cleanList = roughList.toString();
        return List.of(cleanList.split(","));
    }
}
