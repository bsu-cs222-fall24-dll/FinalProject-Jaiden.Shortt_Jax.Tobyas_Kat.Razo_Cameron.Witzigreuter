package edu.bsu.cs.records;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RunStorageTest {
    @Test
    public void test_playernamesForLeaderboard() {
        RunStorage singlePlayerRun = new RunStorage(
                null, null, null, null, null,
                List.of(new PlayerStorage(null, null, null, "player")),
                null, null
        );
        Assertions.assertEquals("player", singlePlayerRun.playernamesForLeaderboard());

        RunStorage twoPlayerRun = new RunStorage(
                null, null, null, null, null,
                List.of(
                        new PlayerStorage(null, null, null, "player"),
                        new PlayerStorage(null, null, null, "another player")
                ),
                null, null
        );
        Assertions.assertEquals("player [+]", twoPlayerRun.playernamesForLeaderboard());
    }

    @Test
    public void test_prettyDateSubmitted() {
        RunStorage run = new RunStorage(
                null, null, null, null, null, null, "2024-01-21T12:20:16Z", null
        );

        Assertions.assertEquals("10 months ago", run.prettyDateSubmitted());
    }
}
