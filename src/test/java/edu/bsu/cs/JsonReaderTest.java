package edu.bsu.cs;

import edu.bsu.cs.records.*;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        levelListReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-levels.json"), StandardCharsets.UTF_8));
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
            levelListReader,
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
    public void test_pathExists() {
        Assertions.assertTrue(exampleReader.test_pathExists("example_map_key.key_3"));
        Assertions.assertFalse(exampleReader.test_pathExists("example_map_key.key_4"));
    }


    @Test
    public void test_createGame() {
        GameStorage expectedGame = new GameStorage(
                    "https://www.speedrun.com/sms",
                    "https://www.speedrun.com/api/v1/games/v1pxjz68",
                    "v1pxjz68",
                    "Super Mario Sunshine",

                    "https://www.speedrun.com/api/v1/games/v1pxjz68/categories",
                    "https://www.speedrun.com/api/v1/games/v1pxjz68/levels"
        );
        GameStorage actualGame = gameReader.createGame();

        Assertions.assertEquals(expectedGame, actualGame);
    }

    @Test
    public void test_createCategoryList() {
        List<CategoryStorage> expectedCategoryList = List.of(
                new CategoryStorage("https://www.speedrun.com/sms#Any", "https://www.speedrun.com/api/v1/categories/n2y3r8do", "n2y3r8do", "Any%", "per-game", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms#120_Shines", "https://www.speedrun.com/api/v1/categories/z27o9gd0", "z27o9gd0", "120 Shines", "per-game", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms#96_Shines", "https://www.speedrun.com/api/v1/categories/xk9n9y20", "xk9n9y20", "96 Shines", "per-game", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms#79_Shines", "https://www.speedrun.com/api/v1/categories/7kjqlxd3", "7kjqlxd3", "79 Shines", "per-game", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms#All_Episodes", "https://www.speedrun.com/api/v1/categories/wkpmjjkr", "wkpmjjkr", "All Episodes", "per-game", "https://www.speedrun.com/api/v1/games/v1pxjz68"),

                new CategoryStorage("https://www.speedrun.com/sms", "https://www.speedrun.com/api/v1/categories/xd1r95wk", "xd1r95wk", "Individual World", "per-level", "https://www.speedrun.com/api/v1/games/v1pxjz68"),

                new CategoryStorage("https://www.speedrun.com/sms#Any_Hoverless", "https://www.speedrun.com/api/v1/categories/xd14l37d", "xd14l37d", "Any% Hoverless", "per-game", "https://www.speedrun.com/api/v1/games/v1pxjz68"),

                new CategoryStorage("https://www.speedrun.com/sms", "https://www.speedrun.com/api/v1/categories/w203veo2", "w203veo2", "All Shines", "per-level", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms", "https://www.speedrun.com/api/v1/categories/wdm6lm3k", "wdm6lm3k", "Full Completion", "per-level", "https://www.speedrun.com/api/v1/games/v1pxjz68"),

                new CategoryStorage("https://www.speedrun.com/sms#All_Blue_Coins", "https://www.speedrun.com/api/v1/categories/ndx4ywj2", "ndx4ywj2", "All Blue Coins", "per-game", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new CategoryStorage("https://www.speedrun.com/sms#20_Shines", "https://www.speedrun.com/api/v1/categories/xk9e86v2", "xk9e86v2", "20 Shines", "per-game", "https://www.speedrun.com/api/v1/games/v1pxjz68")
        );
        List<CategoryStorage> actualCategoryList = categoryListReader.createCategoryList();

        Assertions.assertEquals(expectedCategoryList, actualCategoryList);
    }

    @Test
    public void test_createLevelList() {
        List<LevelStorage> expectedLevelList = List.of(
                new LevelStorage("https://www.speedrun.com/sms/Bianco_Hills", "https://www.speedrun.com/api/v1/levels/xd4e80wm", "xd4e80wm", "Bianco Hills", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Bianco_Hills_Hoverless", "https://www.speedrun.com/api/v1/levels/nwlzepdv", "nwlzepdv", "Bianco Hills Hoverless", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Ricco_Harbor", "https://www.speedrun.com/api/v1/levels/xd0no09q", "xd0no09q", "Ricco Harbor", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Ricco_Harbor_Hoverless", "https://www.speedrun.com/api/v1/levels/rw6gyn97", "rw6gyn97", "Ricco Harbor Hoverless", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Gelato_Beach", "https://www.speedrun.com/api/v1/levels/n93l3790", "n93l3790", "Gelato Beach", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Gelato_Beach_Hoverless", "https://www.speedrun.com/api/v1/levels/z985l79l", "z985l79l", "Gelato Beach Hoverless", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Pinna_Park", "https://www.speedrun.com/api/v1/levels/rdnxgnwm", "rdnxgnwm", "Pinna Park", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Pinna_Park_Hoverless", "https://www.speedrun.com/api/v1/levels/ldyk0jw3", "ldyk0jw3", "Pinna Park Hoverless", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Sirena_Beach", "https://www.speedrun.com/api/v1/levels/ywe3pq9l", "ywe3pq9l", "Sirena Beach", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Sirena_Beach_Hoverless", "https://www.speedrun.com/api/v1/levels/69z606d1", "69z606d1", "Sirena Beach Hoverless", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Noki_Bay", "https://www.speedrun.com/api/v1/levels/r9gn8qd2", "r9gn8qd2", "Noki Bay", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Noki_Bay_Hoverless", "https://www.speedrun.com/api/v1/levels/o9xo069l", "o9xo069l", "Noki Bay Hoverless", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Pianta_Village", "https://www.speedrun.com/api/v1/levels/495zn29p", "495zn29p", "Pianta Village", "https://www.speedrun.com/api/v1/games/v1pxjz68"),
                new LevelStorage("https://www.speedrun.com/sms/Pianta_Village_Hoverless", "https://www.speedrun.com/api/v1/levels/rdqoqmwx", "rdqoqmwx", "Pianta Village Hoverless", "https://www.speedrun.com/api/v1/games/v1pxjz68")
        );
        List<LevelStorage> actualLevelList = levelListReader.createLevelList();

        Assertions.assertEquals(expectedLevelList, actualLevelList);
    }

    @Test
    public void test_createLeaderboard() throws IOException {
        PlayerStorage guest = guestReader.createGuest();
        PlayerStorage user = userReader.createUser();
        List<RunStorage> expectedRuns = List.of(
            new RunStorage("https://www.speedrun.com/sms/run/zppv46rz", "https://www.speedrun.com/api/v1/runs/zppv46rz", "zppv46rz", "https://www.speedrun.com/api/v1/games/v1pxjz68", "https://www.speedrun.com/api/v1/categories/z27o9gd0", List.of(guest, user), 1, "2024-01-21T12:20:16Z", "PT2H51M34S"),
            new RunStorage("https://www.speedrun.com/sms/run/megl1l9y", "https://www.speedrun.com/api/v1/runs/megl1l9y", "megl1l9y", "https://www.speedrun.com/api/v1/games/v1pxjz68", "https://www.speedrun.com/api/v1/categories/n2y3r8do", List.of(guest, user), 2, "2024-09-17T02:51:23Z", "PT23M57S"),
            new RunStorage("https://www.speedrun.com/sms/run/yoxx36dy", "https://www.speedrun.com/api/v1/runs/yoxx36dy", "yoxx36dy", "https://www.speedrun.com/api/v1/games/v1pxjz68", "https://www.speedrun.com/api/v1/categories/n2y3r8do", List.of(guest, user), 3, "2024-10-05T05:59:20Z", "PT24M45S")
        );

        LeaderboardStorage expectedLeaderboard = new LeaderboardStorage(
                "https://www.speedrun.com/sms#120_Shines",
                "https://www.speedrun.com/api/v1/games/v1pxjz68",
                "https://www.speedrun.com/api/v1/categories/z27o9gd0",
                null,

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

                1,
                "2024-01-21T12:20:16Z",
                "PT2H51M34S"
        );
        RunStorage actualRunStorage = runReader.test_createRun(1);

        Assertions.assertEquals(expectedRunStorage, actualRunStorage);
    }
}
