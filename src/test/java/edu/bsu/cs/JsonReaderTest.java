package edu.bsu.cs;

import edu.bsu.cs.records.*;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-game-with-embeds.json"), StandardCharsets.UTF_8));
        leaderboardReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-leaderboard.json"), StandardCharsets.UTF_8));
        justRunsReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-runs-by-date.json"), StandardCharsets.UTF_8));
        guestReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/example-guest.json"), StandardCharsets.UTF_8));
        userReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/example-user.json"), StandardCharsets.UTF_8));

        runBySubmittedReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/example-run-bysubmitted.json"), StandardCharsets.UTF_8));
        runByDateReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/example-run-bydate.json"), StandardCharsets.UTF_8));
        runByDateVerifiedReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/example-run-null-verifiedDate.json"), StandardCharsets.UTF_8));
        runByNullDateReader = new JsonReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/example-run-null-notverified.json"), StandardCharsets.UTF_8));
    }
    static JsonReader exampleReader,
            gameReader,
            leaderboardReader,
            justRunsReader,
            guestReader,
            userReader,

            runBySubmittedReader,
            runByDateReader,
            runByDateVerifiedReader,
            runByNullDateReader;


    @Test
    public void throwIOExceptionForBadJson() {
        boolean statusExceptionWasThrown = false;

        try {
            @SuppressWarnings("unused")
            JsonReader jsonReaderWithBadJson =
                    new JsonReader(IOUtils.toString((
                            new FileInputStream("src/test/resources/edu.bsu.cs/status-404.json")), StandardCharsets.UTF_8));
        }
        catch (FileNotFoundException ignored) {}
        catch (IOException jsonReaderThrewStatusException) {
            statusExceptionWasThrown = true;
        }
        finally {
            Assertions.assertTrue(statusExceptionWasThrown);
        }
    }


    // Some tests here have unchecked casts. This is the nature of JsonPath.
    // See the suppression at JsonReader.scan() for more.
    @Test
    public void test_scan_String() {
        String expected = "example_string_value";
        String actual = (String) exampleReader.scan("example_string_key");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test_scan_int() {
        int expected = 0;
        int actual = (int) exampleReader.scan("example_int_key");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test_scan_double() {
        double expected = 1.1;
        double actual = (double) exampleReader.scan("example_double_key");

        Assertions.assertEquals(expected, actual);
    }

    @Test @SuppressWarnings("unchecked")
    public void test_scan_arrayAsList() {
        List<Integer> expected = List.of(1, 2, 3, 4, 5);
        List<Integer> actual = (List<Integer>) exampleReader.scan("example_array_key");

        Assertions.assertEquals(expected, actual);
    }

    @Test @SuppressWarnings("unchecked")
    public void test_scan_Map() {
        Map<String, String> expected = Map.of("key_1", "value_1", "key_2", "value_2", "key_3", "value_3");
        Map<String, String> actual = (Map<String, String>) exampleReader.scan("example_map_key");

        Assertions.assertEquals(expected, actual);
    }

    @Test @SuppressWarnings("unchecked")
    public void test_scan_MapOfMaps() {
        Map<String, Map<String, String>> expected = Map.of(
                "map_1", Map.of("key_1", "value_a", "key_2", "value_b"),
                "map_2", Map.of("key_1", "value_i", "key_2", "value_ii"),
                "map_3", Map.of("key_1", "value_alpha", "key_2", "value_beta")
        );
        Map<String, Map<String, String>> actual =
                (Map<String, Map<String, String>>) exampleReader.scan("example_map_of_maps_key");

        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void test_pathExists() {
        Assertions.assertTrue(exampleReader.pathExists("example_map_key.key_3"));
        Assertions.assertFalse(exampleReader.pathExists("example_map_key.key_4"));
    }


    @Test
    public void test_createGame() {
        GameStorage expectedGame = new GameStorage(
                "v1pxjz68", "Super Mario Sunshine",
                getCategoriesForTest(),
                getLevelsForTest()
        );
        GameStorage actualGame = gameReader.createGame();

        Assertions.assertEquals(expectedGame, actualGame);
    }
    private List<CategoryStorage> getCategoriesForTest() {
        return List.of(
                new CategoryStorage("n2y3r8do", "Any%", "per-game"),
                new CategoryStorage("z27o9gd0", "120 Shines", "per-game"),
                new CategoryStorage("xk9n9y20", "96 Shines", "per-game"),
                new CategoryStorage("7kjqlxd3", "79 Shines", "per-game"),
                new CategoryStorage("wkpmjjkr", "All Episodes", "per-game"),
                new CategoryStorage("xd1r95wk", "Individual World", "per-level"),
                new CategoryStorage("xd14l37d", "Any% Hoverless", "per-game"),
                new CategoryStorage("w203veo2", "All Shines", "per-level"),
                new CategoryStorage("wdm6lm3k", "Full Completion", "per-level"),
                new CategoryStorage("ndx4ywj2", "All Blue Coins", "per-game"),
                new CategoryStorage("xk9e86v2", "20 Shines", "per-game")
        );
    }
    private List<LevelStorage> getLevelsForTest() {
        return List.of(
                new LevelStorage("xd4e80wm", "Bianco Hills"),
                new LevelStorage("nwlzepdv", "Bianco Hills Hoverless"),
                new LevelStorage("xd0no09q", "Ricco Harbor"),
                new LevelStorage("rw6gyn97", "Ricco Harbor Hoverless"),
                new LevelStorage("n93l3790", "Gelato Beach"),
                new LevelStorage("z985l79l", "Gelato Beach Hoverless"),
                new LevelStorage("rdnxgnwm", "Pinna Park"),
                new LevelStorage("ldyk0jw3", "Pinna Park Hoverless"),
                new LevelStorage("ywe3pq9l", "Sirena Beach"),
                new LevelStorage("69z606d1", "Sirena Beach Hoverless"),
                new LevelStorage("r9gn8qd2", "Noki Bay"),
                new LevelStorage("o9xo069l", "Noki Bay Hoverless"),
                new LevelStorage("495zn29p", "Pianta Village"),
                new LevelStorage("rdqoqmwx", "Pianta Village Hoverless")
        );
    }

    @Test
    public void test_createLeaderboard() {
        LeaderboardStorage expectedLeaderboard = getLeaderboardForTest();
        LeaderboardStorage actualLeaderboard = leaderboardReader.test_createLeaderboard(false);

        Assertions.assertEquals(expectedLeaderboard, actualLeaderboard);
    }
    private LeaderboardStorage getLeaderboardForTest() {
        List<RunStorage> runs = List.of(
                new RunStorage(
                        List.of(new PlayerStorage("https://www.speedrun.com/api/v1/users/98r1n2qj")),
                        "2024-01-21T12:20:16Z", "PT2H51M34S"
                ),
                new RunStorage(
                        List.of(new PlayerStorage("https://www.speedrun.com/api/v1/users/o8663m38")),
                        "2024-05-17T04:19:16Z", "PT2H51M45S"
                ),
                new RunStorage(
                        List.of(new PlayerStorage("https://www.speedrun.com/api/v1/users/98r1n6j1")),
                        "2024-09-16T05:55:56Z", "PT2H53M13S"
                )
        );
        List<String> places = List.of("1", "2", "3");

        return new LeaderboardStorage(runs, places);
    }

    @Test
    public void test_createLeaderboard_justRuns() {
        LeaderboardStorage expectedLeaderboard = getLeaderboardForTest_justRuns();
        LeaderboardStorage actualLeaderboard = justRunsReader.test_createLeaderboard(true);

        Assertions.assertEquals(expectedLeaderboard, actualLeaderboard);
    }
    private LeaderboardStorage getLeaderboardForTest_justRuns() {
        List<RunStorage> runs = List.of(
                new RunStorage(
                        List.of(new PlayerStorage("https://www.speedrun.com/api/v1/users/dx3ml28l")),
                        "2024-11-12T15:49:30Z", "PT2H41M28S"
                ),
                new RunStorage(
                        List.of(new PlayerStorage("https://www.speedrun.com/api/v1/users/86rd5958")),
                        "2024-11-10T15:15:52Z", "PT4H52M34S"
                ),
                new RunStorage(
                        List.of(new PlayerStorage("https://www.speedrun.com/api/v1/users/18v6knxl")),
                        "2024-11-07T15:50:43Z", "PT3H24M18S"
                )
        );
        List<String> places = List.of("#", "#", "#");

        return new LeaderboardStorage(runs, places);
    }


    @Test
    public void test_getDateSubmitted_submitted() {
        String expectedDateSubmitted = "2024-01-21T12:20:16Z";
        String actualDateSubmitted = runBySubmittedReader.getDateSubmitted("data");

        Assertions.assertEquals(expectedDateSubmitted, actualDateSubmitted);
    }
    @Test
    public void test_getDateSubmitted_date() {
        String expectedDateSubmitted = "2024-01-21";
        String actualDateSubmitted = runByDateReader.getDateSubmitted("data");

        Assertions.assertEquals(expectedDateSubmitted, actualDateSubmitted);
    }
    @Test
    public void test_getDateSubmitted_verifiedDate() {
        String expectedDateSubmitted = "2024-01-21";
        String actualDateSubmitted = runByDateVerifiedReader.getDateSubmitted("data");

        Assertions.assertEquals(expectedDateSubmitted, actualDateSubmitted);
    }
    @Test
    public void test_getDateSubmitted_null() {
        String dateSubmitted = runByNullDateReader.getDateSubmitted("data");

        Assertions.assertNull(dateSubmitted);
    }


    @Test
    public void test_createPlayer_user() {
        PlayerStorage expectedPlayer = new PlayerStorage("chewdiggy");
        PlayerStorage actualPlayer = userReader.createPlayer();

        Assertions.assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    public void test_createPlayer_guest() {
        PlayerStorage expectedPlayer = new PlayerStorage("Alex");
        PlayerStorage actualPlayer = guestReader.createPlayer();

        Assertions.assertEquals(expectedPlayer, actualPlayer);
    }
}