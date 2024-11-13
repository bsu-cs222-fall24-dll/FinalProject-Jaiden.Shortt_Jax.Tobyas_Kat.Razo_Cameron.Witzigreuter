package edu.bsu.cs;

import edu.bsu.cs.records.*;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonReaderTest {
    @BeforeAll
    public static void initializeJsonReaders() throws IOException {
        exampleReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/example.json"), StandardCharsets.UTF_8));
        gameReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-game.json"), StandardCharsets.UTF_8));
        categoryListReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-categories.json"), StandardCharsets.UTF_8));
        leaderboardReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-leaderboard.json"), StandardCharsets.UTF_8));
        runReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-run1.json"), StandardCharsets.UTF_8));
        guestReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/example-guest.json"), StandardCharsets.UTF_8));
        userReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/example-user.json"), StandardCharsets.UTF_8));
    }
    static JsonReader exampleReader,
            gameReader,
            categoryListReader,
            leaderboardReader,
            runReader,
            guestReader,
            userReader;

    @Test
    public void test_definiteScan_String() {
        String expected = "example_string_value";
        String actual = (String) exampleReader.test_definiteScan("example_string_key");

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void test_definiteScan_int() {
        int expected = 0;
        int actual = (int) exampleReader.test_definiteScan("example_int_key");

        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void test_definiteScan_double() {
        double expected = 1.1;
        double actual = (double) exampleReader.test_definiteScan("example_double_key");

        Assertions.assertEquals(expected, actual);
    }
    @Test @SuppressWarnings("unchecked")
    public void test_definiteScan_arrayAsList() {
        List<Integer> expected = List.of(1, 2, 3, 4, 5);
        List<Integer> actual = (List<Integer>) exampleReader.test_definiteScan("example_array_key");

        Assertions.assertEquals(expected, actual);
    }
    @Test @SuppressWarnings("unchecked")
    public void test_definiteScan_Map() {
        Map<String, String> expected = Map.of("key_1", "value_1", "key_2", "value_2", "key_3", "value_3");
        Map<String, String> actual = (Map<String, String>) exampleReader.test_definiteScan("example_map_key");

        Assertions.assertEquals(expected, actual);
    }
    @Test @SuppressWarnings("unchecked")
    public void test_definiteScan_MapOfMaps() {
        Map<String, Map<String, String>> expected = Map.of(
                "map_1", Map.of("key_1", "value_a", "key_2", "value_b"),
                "map_2", Map.of("key_1", "value_i", "key_2", "value_ii"),
                "map_3", Map.of("key_1", "value_alpha", "key_2", "value_beta")
        );
        Map<String, Map<String, String>> actual =
                (Map<String, Map<String, String>>) exampleReader.test_definiteScan("example_map_of_maps_key");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test_indefiniteScan() {
        List<String> expected = List.of("value_1", "value_1A", "value_1B", "value_a", "value_i", "value_alpha");
        List<String> actual = exampleReader.test_indefiniteScan("key_1");

        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void test_createGame() {
        GameStorage expectedGame = new GameStorage(
                    "https://www.speedrun.com/sms",
                    "https://www.speedrun.com/api/v1/games/v1pxjz68",
                    "v1pxjz68",
                    "Super Mario Sunshine",

                    "https://www.speedrun.com/api/v1/games/v1pxjz68/categories"
        );
        GameStorage actualGame = gameReader.createGame();

        Assertions.assertEquals(expectedGame, actualGame);
    }

    @Test
    public void test_createCategoryList() {
        List<CategoryStorage> expectedCategoryList = List.of(
                new CategoryStorage("https://www.speedrun.com/sms#Any", "https://www.speedrun.com/api/v1/categories/n2y3r8do", "n2y3r8do", "Any%", "https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/n2y3r8do", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms#120_Shines", "https://www.speedrun.com/api/v1/categories/z27o9gd0", "z27o9gd0", "120 Shines", "https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/z27o9gd0", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms#96_Shines", "https://www.speedrun.com/api/v1/categories/xk9n9y20", "xk9n9y20", "96 Shines", "https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/xk9n9y20", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms#79_Shines", "https://www.speedrun.com/api/v1/categories/7kjqlxd3", "7kjqlxd3", "79 Shines", "https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/7kjqlxd3", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms#All_Episodes", "https://www.speedrun.com/api/v1/categories/wkpmjjkr", "wkpmjjkr", "All Episodes", "https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/wkpmjjkr", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms#Any_Hoverless", "https://www.speedrun.com/api/v1/categories/xd14l37d", "xd14l37d", "Any% Hoverless", "https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/xd14l37d", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms#All_Blue_Coins", "https://www.speedrun.com/api/v1/categories/ndx4ywj2", "ndx4ywj2", "All Blue Coins", "https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/ndx4ywj2", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms#20_Shines", "https://www.speedrun.com/api/v1/categories/xk9e86v2", "xk9e86v2", "20 Shines", "https://www.speedrun.com/api/v1/leaderboards/v1pxjz68/category/xk9e86v2", "https://www.speedrun.com/api/v1/games/v1pxjz68")
        );
        List<CategoryStorage> actualCategoryList = categoryListReader.createCategoryList();

        Assertions.assertEquals(expectedCategoryList, actualCategoryList);
    }

    // This method also tests createRun()
    @Test
    public void test_createLeaderboard() throws IOException {
        LinkedHashMap<Integer, RunStorage> expectedRuns = new LinkedHashMap<>();
        PlayerStorage guest = guestReader.createGuest();
        PlayerStorage user = userReader.createUser();
        expectedRuns.put(1, new RunStorage("https://www.speedrun.com/sms/run/zppv46rz", "https://www.speedrun.com/api/v1/runs/zppv46rz", "zppv46rz", "https://www.speedrun.com/api/v1/games/v1pxjz68", "https://www.speedrun.com/api/v1/categories/z27o9gd0", List.of(guest, user), "2024-01-21T12:20:16Z", "PT2H51M34S"));
        expectedRuns.put(2, new RunStorage("https://www.speedrun.com/sms/run/megl1l9y", "https://www.speedrun.com/api/v1/runs/megl1l9y", "megl1l9y", "https://www.speedrun.com/api/v1/games/v1pxjz68", "https://www.speedrun.com/api/v1/categories/n2y3r8do", List.of(guest, user), "2024-09-17T02:51:23Z", "PT23M57S"));
        expectedRuns.put(3, new RunStorage("https://www.speedrun.com/sms/run/yoxx36dy", "https://www.speedrun.com/api/v1/runs/yoxx36dy", "yoxx36dy", "https://www.speedrun.com/api/v1/games/v1pxjz68", "https://www.speedrun.com/api/v1/categories/n2y3r8do", List.of(guest, user), "2024-10-05T05:59:20Z", "PT24M45S"));

        LeaderboardStorage expectedLeaderboard = new LeaderboardStorage(
                "https://www.speedrun.com/sms#120_Shines",
                "https://www.speedrun.com/api/v1/games/v1pxjz68",
                "https://www.speedrun.com/api/v1/categories/z27o9gd0",

                "realtime",
                expectedRuns
        );
        LeaderboardStorage actualLeaderboard = leaderboardReader.test_createLeaderboard();

        Assertions.assertEquals(expectedLeaderboard, actualLeaderboard);
    }

    @Test
    public void test_createRun() throws IOException {
        RunStorage expectedRunStorage = new RunStorage(
                "https://www.speedrun.com/sms/run/zppv46rz",
                "https://www.speedrun.com/api/v1/runs/zppv46rz",
                "zppv46rz",

                "https://www.speedrun.com/api/v1/games/v1pxjz68",
                "https://www.speedrun.com/api/v1/categories/z27o9gd0",
                List.of(
                        guestReader.createGuest(),
                        userReader.createUser()
                ),

                "2024-01-21T12:20:16Z",
                "PT2H51M34S"
        );
        RunStorage actualRunStorage = runReader.test_createRun();

        Assertions.assertEquals(expectedRunStorage, actualRunStorage);
    }
}
