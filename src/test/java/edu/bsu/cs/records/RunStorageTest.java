package edu.bsu.cs.records;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RunStorageTest {
    @Test
    public void test_playernamesForLeaderboard_onePlayer() {
        RunStorage runWithOnePlayer = new RunStorage(
                List.of(new PlayerStorage("player", "selflink")),
                "date", "time"
        );

        String expectedPlayernames = "player";
        String actualPlayernames = runWithOnePlayer.playernamesForLeaderboard();

        Assertions.assertEquals(expectedPlayernames, actualPlayernames);
    }

    @Test
    public void test_playernamesForLeaderboard_morePlayers() {
        RunStorage runWithMorePlayers = new RunStorage(
                List.of(new PlayerStorage("player 1", "selflink 1"),
                        new PlayerStorage("player 2", "selflink 2")),
                "date", "time"
        );

        String expectedPlayernames = "player 1, ...";
        String actualPlayernames = runWithMorePlayers.playernamesForLeaderboard();

        Assertions.assertEquals(expectedPlayernames, actualPlayernames);
    }


    @Test
    public void test_prettyDateSubmitted_fromLocalDateTime() {
        RunStorage runWithLocalDateTime = new RunStorage(
                List.of(), "2024-01-21T12:20:16Z", "time"
        );

        String expectedPrettyDateAtTimeOfWriting = "10 months ago";
        String actualPrettyDate = runWithLocalDateTime.prettyDateSubmitted();

        Assertions.assertEquals(expectedPrettyDateAtTimeOfWriting, actualPrettyDate);
    }

    @Test
    public void test_prettyDateSubmitted_fromLocalDate() {
        RunStorage runWithLocalDate = new RunStorage(
                List.of(), "2024-10-05", "time"
        );

        // Rounds the month up or down by 15 days since
        String expectedPrettyDateAtTimeOfWriting = "2 months ago";
        String actualPrettyDate = runWithLocalDate.prettyDateSubmitted();

        Assertions.assertEquals(expectedPrettyDateAtTimeOfWriting, actualPrettyDate);
    }

    @Test
    public void test_prettyDateSubmitted_fromNull() {
        RunStorage runWithNullDate = new RunStorage(
                List.of(), null, "time"
        );

        String expectedPrettyDate = "<no date found>";
        String actualPrettyDate = runWithNullDate.prettyDateSubmitted();

        Assertions.assertEquals(expectedPrettyDate, actualPrettyDate);
    }
}
