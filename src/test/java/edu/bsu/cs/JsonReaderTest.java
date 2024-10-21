package edu.bsu.cs;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JsonReaderTest {
    static JsonReader jsonExampleReader;

    @BeforeAll
    public static void initializeExampleReader() {
        try (FileInputStream jsonExampleStream = new FileInputStream("src/test/resources/edu.bsu.cs/example.json")) {
            jsonExampleReader = JsonReader.createReader(IOUtils.toString(jsonExampleStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("File could not be found.%n");
            jsonExampleReader = JsonReader.createReader("{\"name\":\"example.json\",\"example_key\":\"example_value\",\"example_array\":[\"item 1\",\"item 2\",\"item 3\"],\"example_map\":{\"key 1\":\"value 1\",\"key 2\":\"value 2\",\"key 3\":\"value 3\"},\"example_map_array\":[{\"key A\":\"value A\",\"key B\":\"value B\"},{\"key C\":\"value C\",\"key D\":\"value D\"},{\"key E\":\"value E\",\"key F\":\"value F\"}],\"example_for_deep_scan\":[{\"name\":\"Jaiden Shortt\",\"username\":\"JaidenShortt\",\"list\":[\"a\",\"b\",\"c\"],\"team\":\"D\"},{\"name\":\"Jax Tobyas\",\"username\":\"JaxT05\",\"list\":[\"a\",\"b\",\"c\"],\"team\":\"D\"},{\"name\":\"Kat Razo\",\"username\":\"katrazo\",\"list\":[\"a\",\"b\",\"c\"],\"team\":\"D\"},{\"name\":\"Cameron Witzigreuter\",\"username\":\"Cameron311\",\"list\":[\"a\",\"b\",\"c\"],\"team\":\"D\"}]}");
        }
    }

    @Test
    public void test_getValue() {
        String expectedValue = "example_value";
        String actualValue = jsonExampleReader.getValue("example_key");

        Assertions.assertEquals(expectedValue, actualValue);
    }
    @Test
    public void test_getListOfValues() {
        List<String> expectedValueArray = Arrays.asList("item 1", "item 2", "item 3");
        List<String> actualValueArray = jsonExampleReader.getListOfValues("example_array");

        Assertions.assertEquals(expectedValueArray, actualValueArray);
    }
    @Test
    public void test_getMap() {
        Map<String, String> expectedMap = Map.of(
                "key 1", "value 1",
                "key 2", "value 2",
                "key 3", "value 3"
        );
        Map<String, String> actualMap = jsonExampleReader.getMap("example_map");

        Assertions.assertEquals(expectedMap, actualMap);
    }
    @Test
    public void test_getListOfMaps() {
        List<Map<String, String>> expectedListOfMaps = Arrays.asList(
                Map.of("key A", "value A", "key B", "value B"),
                Map.of("key C", "value C", "key D", "value D"),
                Map.of("key E", "value E", "key F", "value F")
        );
        List<Map<String, String>> actualListOfMaps = jsonExampleReader.getListOfMaps("example_map_array");

        Assertions.assertEquals(expectedListOfMaps, actualListOfMaps);
    }

    @Test
    public void test_getAnyValuesAsList() {
        List<String> expectedValues = Arrays.asList(
                "example.json", "Jaiden Shortt", "Jax Tobyas", "Kat Razo", "Cameron Witzigreuter"
        );
        List<String> actualValues = jsonExampleReader.getAnyValuesAsList("name");

        Assertions.assertEquals(expectedValues, actualValues);
    }
}
