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
    static JsonReader categoriesReader;

    @BeforeAll
    public static void initializeExampleReader() {
        try (FileInputStream jsonExampleStream = new FileInputStream("src/test/resources/edu.bsu.cs/example.json")) {
            jsonExampleReader = JsonReader.createReader(IOUtils.toString(jsonExampleStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("File could not be found.%n");
            jsonExampleReader = JsonReader.createReader("{\"name\":\"example.json\",\"example_key\":\"example_value\",\"example_array\":[\"item 1\",\"item 2\",\"item 3\"],\"example_map\":{\"key 1\":\"value 1\",\"key 2\":\"value 2\",\"key 3\":\"value 3\"},\"example_map_array\":[{\"key A\":\"value A\",\"key B\":\"value B\"},{\"key C\":\"value C\",\"key D\":\"value D\"},{\"key E\":\"value E\",\"key F\":\"value F\"}],\"example_for_deep_scan\":[{\"name\":\"Jaiden Shortt\",\"username\":\"JaidenShortt\",\"list\":[\"a\",\"b\",\"c\"],\"team\":\"D\"},{\"name\":\"Jax Tobyas\",\"username\":\"JaxT05\",\"list\":[\"a\",\"b\",\"c\"],\"team\":\"D\"},{\"name\":\"Kat Razo\",\"username\":\"katrazo\",\"list\":[\"a\",\"b\",\"c\"],\"team\":\"D\"},{\"name\":\"Cameron Witzigreuter\",\"username\":\"Cameron311\",\"list\":[\"a\",\"b\",\"c\"],\"team\":\"D\"}]}");
        }
    }
    @BeforeAll
    public static void initializeCategoriesReader() {
        try (FileInputStream jsonExampleStream = new FileInputStream("src/test/resources/edu.bsu.cs/super-mario-sunshine_categories.json")) {
            categoriesReader = JsonReader.createReader(IOUtils.toString(jsonExampleStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("File could not be found.%n");
            categoriesReader = JsonReader.createReader("{\"data\":[{\"id\":\"n2y3r8do\",\"name\":\"Any%\",\"weblink\":\"https://www.speedrun.com/sms#Any\",\"type\":\"per-game\",\"rules\":\"\",\"players\":{\"type\":\"exactly\",\"value\":1},\"miscellaneous\":false,\"links\":[{\"rel\":\"self\",\"uri\":\"https://www.speedrun.com/api/v1/categories/n2y3r8do\"},{\"rel\":\"game\",\"uri\":\"https://www.speedrun.com/api/v1/games/v1pxjz68\"},{\"rel\":\"variables\",\"uri\":\"https://www.speedrun.com/api/v1/categories/n2y3r8do/variables\"},{\"rel\":\"records\",\"uri\":\"https://www.speedrun.com/api/v1/categories/n2y3r8do/records\"},{\"rel\":\"runs\",\"uri\":\"https://www.speedrun.com/api/v1/runs?category=n2y3r8do\"},{\"rel\":\"leaderboard\",\"uri\":\"https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/n2y3r8do\"}]},{\"id\":\"z27o9gd0\",\"name\":\"120 Shines\",\"weblink\":\"https://www.speedrun.com/sms#120_Shines\",\"type\":\"per-game\",\"rules\":\"**Goal**\\nCollect every Shine Sprite in the game for a grand total of 120 Shine Sprites.\\n\\n**Requirements**\\n\\n- Emulator and Nintendont is not allowed for runs faster than 3 hours 40 minutes.\\r\\n- Video proof is required for runs faster than 3 hours 40 minutes.\",\"players\":{\"type\":\"exactly\",\"value\":1},\"miscellaneous\":false,\"links\":[{\"rel\":\"self\",\"uri\":\"https://www.speedrun.com/api/v1/categories/z27o9gd0\"},{\"rel\":\"game\",\"uri\":\"https://www.speedrun.com/api/v1/games/v1pxjz68\"},{\"rel\":\"variables\",\"uri\":\"https://www.speedrun.com/api/v1/categories/z27o9gd0/variables\"},{\"rel\":\"records\",\"uri\":\"https://www.speedrun.com/api/v1/categories/z27o9gd0/records\"},{\"rel\":\"runs\",\"uri\":\"https://www.speedrun.com/api/v1/runs?category=z27o9gd0\"},{\"rel\":\"leaderboard\",\"uri\":\"https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/z27o9gd0\"}]},{\"id\":\"xk9n9y20\",\"name\":\"96 Shines\",\"weblink\":\"https://www.speedrun.com/sms#96_Shines\",\"type\":\"per-game\",\"rules\":\"**Goal**\\nCollect every Shine Sprite in the game, excluding those obtained from trading in blue coins, for a grand total of 96 Shine Sprites.\\n\\n**Requirements**\\n\\n- Emulator and Nintendont is not allowed for runs faster than 3 hours.\\r\\n- Video proof is required for runs faster than 3 hours.\",\"players\":{\"type\":\"exactly\",\"value\":1},\"miscellaneous\":false,\"links\":[{\"rel\":\"self\",\"uri\":\"https://www.speedrun.com/api/v1/categories/xk9n9y20\"},{\"rel\":\"game\",\"uri\":\"https://www.speedrun.com/api/v1/games/v1pxjz68\"},{\"rel\":\"variables\",\"uri\":\"https://www.speedrun.com/api/v1/categories/xk9n9y20/variables\"},{\"rel\":\"records\",\"uri\":\"https://www.speedrun.com/api/v1/categories/xk9n9y20/records\"},{\"rel\":\"runs\",\"uri\":\"https://www.speedrun.com/api/v1/runs?category=xk9n9y20\"},{\"rel\":\"leaderboard\",\"uri\":\"https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/xk9n9y20\"}]},{\"id\":\"xd1r95wk\",\"name\":\"Individual World\",\"weblink\":\"https://www.speedrun.com/sms\",\"type\":\"per-level\",\"rules\":\"Complete episodes 1-7.\\r\\nIn Bianco Hills, you may skip episode 1.\\r\\nIn Pinna Park, you may perform Early Yoshi Go-round instead of episodes 5 and 6.\\r\\nAll forms of Gelato Beach Skip are banned.\\r\\nDo not use FLUDD in secret levels.\\r\\n\\r\\n\\r\\n- Timing starts when you select the first shine and ends when Mario holds up the final shine.\\r\\n- 1st place runs must include video.\\r\\n- Emulator, SD and USB are all banned.\",\"players\":{\"type\":\"exactly\",\"value\":1},\"miscellaneous\":false,\"links\":[{\"rel\":\"self\",\"uri\":\"https://www.speedrun.com/api/v1/categories/xd1r95wk\"},{\"rel\":\"game\",\"uri\":\"https://www.speedrun.com/api/v1/games/v1pxjz68\"},{\"rel\":\"variables\",\"uri\":\"https://www.speedrun.com/api/v1/categories/xd1r95wk/variables\"},{\"rel\":\"records\",\"uri\":\"https://www.speedrun.com/api/v1/categories/xd1r95wk/records\"},{\"rel\":\"runs\",\"uri\":\"https://www.speedrun.com/api/v1/runs?category=xd1r95wk\"}]},{\"id\":\"xd14l37d\",\"name\":\"Any% Hoverless\",\"weblink\":\"https://www.speedrun.com/sms#Any_Hoverless\",\"type\":\"per-game\",\"rules\":\"**Goal**\\nBeat the game (Defeat Bowser in Corona Mountain) without using the Hover Nozzle.\\n\\n**Requirements**\\n\\n- Emulator and Nintendont is not allowed for runs faster than 1 hour 40 minutes.\\r\\n- Video proof is required for runs faster than 1 hour 40 minutes.\",\"players\":{\"type\":\"exactly\",\"value\":1},\"miscellaneous\":false,\"links\":[{\"rel\":\"self\",\"uri\":\"https://www.speedrun.com/api/v1/categories/xd14l37d\"},{\"rel\":\"game\",\"uri\":\"https://www.speedrun.com/api/v1/games/v1pxjz68\"},{\"rel\":\"variables\",\"uri\":\"https://www.speedrun.com/api/v1/categories/xd14l37d/variables\"},{\"rel\":\"records\",\"uri\":\"https://www.speedrun.com/api/v1/categories/xd14l37d/records\"},{\"rel\":\"runs\",\"uri\":\"https://www.speedrun.com/api/v1/runs?category=xd14l37d\"},{\"rel\":\"leaderboard\",\"uri\":\"https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/xd14l37d\"}]},{\"id\":\"w203veo2\",\"name\":\"All Shines\",\"weblink\":\"https://www.speedrun.com/sms\",\"type\":\"per-level\",\"rules\":\"Collect all 11 Shines in the world (8 episodes, 2 hidden Shines, 100 coins)\\r\\nDo not use FLUDD when collecting the first Shines in Secret levels (e.g. Bianco 3, Pinna 2)\\r\\n\\r\\n- Timing starts when you select the first shine and ends when Mario holds up the final shine.\\r\\n- 1st place runs must include video.\\r\\n- Emulator, SD and USB are all banned.\",\"players\":{\"type\":\"exactly\",\"value\":1},\"miscellaneous\":false,\"links\":[{\"rel\":\"self\",\"uri\":\"https://www.speedrun.com/api/v1/categories/w203veo2\"},{\"rel\":\"game\",\"uri\":\"https://www.speedrun.com/api/v1/games/v1pxjz68\"},{\"rel\":\"variables\",\"uri\":\"https://www.speedrun.com/api/v1/categories/w203veo2/variables\"},{\"rel\":\"records\",\"uri\":\"https://www.speedrun.com/api/v1/categories/w203veo2/records\"},{\"rel\":\"runs\",\"uri\":\"https://www.speedrun.com/api/v1/runs?category=w203veo2\"}]},{\"id\":\"wdm6lm3k\",\"name\":\"Full Completion\",\"weblink\":\"https://www.speedrun.com/sms\",\"type\":\"per-level\",\"rules\":\"Complete all shines and collect every blue coin in the world.\\r\\nDo not use FLUDD when collecting the first Shines in Secret levels (e.g. Bianco 3, Pinna 2)\\r\\n\\r\\n- Timing starts when you select the first shine and ends when Mario holds up the final shine.\\r\\n- 1st place runs must include video.\\r\\n- Emulator, SD and USB are all banned.\",\"players\":{\"type\":\"exactly\",\"value\":1},\"miscellaneous\":false,\"links\":[{\"rel\":\"self\",\"uri\":\"https://www.speedrun.com/api/v1/categories/wdm6lm3k\"},{\"rel\":\"game\",\"uri\":\"https://www.speedrun.com/api/v1/games/v1pxjz68\"},{\"rel\":\"variables\",\"uri\":\"https://www.speedrun.com/api/v1/categories/wdm6lm3k/variables\"},{\"rel\":\"records\",\"uri\":\"https://www.speedrun.com/api/v1/categories/wdm6lm3k/records\"},{\"rel\":\"runs\",\"uri\":\"https://www.speedrun.com/api/v1/runs?category=wdm6lm3k\"}]},{\"id\":\"ndx4ywj2\",\"name\":\"All Blue Coins\",\"weblink\":\"https://www.speedrun.com/sms#All_Blue_Coins\",\"type\":\"per-game\",\"rules\":\"**Goal**\\nCollect and trade in all 240 blue coins, then beat the game (Defeat Bowser in Corona Mountain), for a grand total of 68 Shine Sprites.\\n\\n**Requirements**\\n\\n- Emulator and Nintendont is not allowed for runs faster than 2 hours 20 minutes.\\r\\n- Video proof is required for runs faster than 2 hours 20 minutes.\",\"players\":{\"type\":\"exactly\",\"value\":1},\"miscellaneous\":false,\"links\":[{\"rel\":\"self\",\"uri\":\"https://www.speedrun.com/api/v1/categories/ndx4ywj2\"},{\"rel\":\"game\",\"uri\":\"https://www.speedrun.com/api/v1/games/v1pxjz68\"},{\"rel\":\"variables\",\"uri\":\"https://www.speedrun.com/api/v1/categories/ndx4ywj2/variables\"},{\"rel\":\"records\",\"uri\":\"https://www.speedrun.com/api/v1/categories/ndx4ywj2/records\"},{\"rel\":\"runs\",\"uri\":\"https://www.speedrun.com/api/v1/runs?category=ndx4ywj2\"},{\"rel\":\"leaderboard\",\"uri\":\"https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/ndx4ywj2\"}]}]}");
        }
    }

    @Test
    public void test_getValue() {
        String expectedValue = "example_value";
        String actualValue = jsonExampleReader.getValue("example_key");

        Assertions.assertEquals(expectedValue, actualValue);
    }
    @Test
    public void test_getBoolean() {
        Assertions.assertTrue(jsonExampleReader.getBoolean("example_boolean"));
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

    @Test
    public void test_readCategory() {
        CategoryStorage smsCategory = categoriesReader.readCategory("data[1]");
        System.out.printf("%nSuper Mario Sunshine: %s%n%n", smsCategory);
    }
}
