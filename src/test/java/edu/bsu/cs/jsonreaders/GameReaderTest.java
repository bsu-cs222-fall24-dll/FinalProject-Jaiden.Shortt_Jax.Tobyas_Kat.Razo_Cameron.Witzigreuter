package edu.bsu.cs.jsonreaders;

import edu.bsu.cs.records.CategoryStorage;
import edu.bsu.cs.records.GameStorage;
import edu.bsu.cs.records.LevelStorage;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GameReaderTest {
    @BeforeAll
    public static void initializeGameReaders() throws IOException {
        smsGameReader = new GameReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/jsonreaders/GameReader/sms-game-with-embeds.json"), StandardCharsets.UTF_8)
        );
    }
    static GameReader smsGameReader;


    @Test
    public void test_throwErrorForBadJson() {
        boolean gameReaderThrewErrorForStatus = false;

        try {
            // The point of this test is not to use the JsonReader,
            // but to test its behavior upon construction.
            @SuppressWarnings("unused")

            JsonReader jsonReaderWithBadJson = new GameReader(
                    IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/jsonreaders/GameReader/status-404.json"), StandardCharsets.UTF_8)
            );
        }
        catch (IOException jsonReaderThrewStatusErrorException) {
            gameReaderThrewErrorForStatus = true;
        }
        finally {
            Assertions.assertTrue(gameReaderThrewErrorForStatus);
        }
    }


    @Test
    public void test_createGame() {
        GameStorage expectedGame = new GameStorage(
                "v1pxjz68", "Super Mario Sunshine",
                getCategoriesForTest(),
                getLevelsForTest()
        );
        GameStorage actualGame = smsGameReader.createGame();

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
}
