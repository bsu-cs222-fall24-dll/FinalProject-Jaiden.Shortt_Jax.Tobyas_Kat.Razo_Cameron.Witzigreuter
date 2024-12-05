package edu.bsu.cs.records;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RunStorageTest {
    @Test
    public void test_prettyDateSubmitted_fromLocalDateTime() {
        RunStorage runWithLocalDateTime = new RunStorage(
                List.of(), "2024-01-21T12:20:16Z", 0
        );

        String expectedPrettyDateAtTimeOfWriting = "10 months ago";
        String actualPrettyDate = runWithLocalDateTime.prettyDateSubmitted();

        Assertions.assertEquals(expectedPrettyDateAtTimeOfWriting, actualPrettyDate);
    }

    @Test
    public void test_prettyDateSubmitted_fromLocalDate() {
        RunStorage runWithLocalDate = new RunStorage(
                List.of(), "2024-10-05", 0
        );

        // Rounds the month up or down by 15 days since
        String expectedPrettyDateAtTimeOfWriting = "2 months ago";
        String actualPrettyDate = runWithLocalDate.prettyDateSubmitted();

        Assertions.assertEquals(expectedPrettyDateAtTimeOfWriting, actualPrettyDate);
    }


    @Test
    public void test_formattedRunTime_allTimesPresent() {
        RunStorage runWithAllTimes = new RunStorage(new ArrayList<>(), "", 90061.001);

        String expectedFormattedRunTime = "1D 1h 01m 01s 001ms";
        String actualFormattedRunTime = runWithAllTimes.formattedRunTime();

        Assertions.assertEquals(expectedFormattedRunTime, actualFormattedRunTime);
    }

    @Test
    public void test_formattedRunTime_noDays() {
        RunStorage runWithAllTimes = new RunStorage(new ArrayList<>(), "", 3661.001);

        String expectedFormattedRunTime = "1h 01m 01s 001ms";
        String actualFormattedRunTime = runWithAllTimes.formattedRunTime();

        Assertions.assertEquals(expectedFormattedRunTime, actualFormattedRunTime);
    }

    @Test
    public void test_formattedRunTime_noDaysHours() {
        RunStorage runWithAllTimes = new RunStorage(new ArrayList<>(), "", 61.001);

        String expectedFormattedRunTime = "1m 01s 001ms";
        String actualFormattedRunTime = runWithAllTimes.formattedRunTime();

        Assertions.assertEquals(expectedFormattedRunTime, actualFormattedRunTime);
    }

    @Test
    public void test_formattedRunTime_noDaysHoursMinutes() {
        RunStorage runWithAllTimes = new RunStorage(new ArrayList<>(), "", 1.001);

        String expectedFormattedRunTime = "1s 001ms";
        String actualFormattedRunTime = runWithAllTimes.formattedRunTime();

        Assertions.assertEquals(expectedFormattedRunTime, actualFormattedRunTime);
    }

    @Test
    public void test_formattedRunTime_noDaysHoursMinutesSeconds() {
        RunStorage runWithAllTimes = new RunStorage(new ArrayList<>(), "", 0.001);

        String expectedFormattedRunTime = "001ms";
        String actualFormattedRunTime = runWithAllTimes.formattedRunTime();

        Assertions.assertEquals(expectedFormattedRunTime, actualFormattedRunTime);
    }

    @Test
    public void test_formattedRunTime_justSeconds() {
        RunStorage runWithAllTimes = new RunStorage(new ArrayList<>(), "", 1);

        String expectedFormattedRunTime = "1s";
        String actualFormattedRunTime = runWithAllTimes.formattedRunTime();

        Assertions.assertEquals(expectedFormattedRunTime, actualFormattedRunTime);
    }

    @Test
    public void test_formattedRunTime_justMinutes() {
        RunStorage runWithAllTimes = new RunStorage(new ArrayList<>(), "", 60);

        String expectedFormattedRunTime = "1m 00s";
        String actualFormattedRunTime = runWithAllTimes.formattedRunTime();

        Assertions.assertEquals(expectedFormattedRunTime, actualFormattedRunTime);
    }

    @Test
    public void test_formattedRunTime_justHours() {
        RunStorage runWithAllTimes = new RunStorage(new ArrayList<>(), "", 3600);

        String expectedFormattedRunTime = "1h 00m 00s";
        String actualFormattedRunTime = runWithAllTimes.formattedRunTime();

        Assertions.assertEquals(expectedFormattedRunTime, actualFormattedRunTime);
    }

    @Test
    public void test_formattedRunTime_justDays() {
        RunStorage runWithAllTimes = new RunStorage(new ArrayList<>(), "", 86400);

        String expectedFormattedRunTime = "1D 00m 00s";
        String actualFormattedRunTime = runWithAllTimes.formattedRunTime();

        Assertions.assertEquals(expectedFormattedRunTime, actualFormattedRunTime);
    }


    @Test
    public void test_playernamesForLeaderboard_onePlayer() {
        RunStorage runWithOnePlayer = new RunStorage(
                List.of(new PlayerStorage(
                        "player",
                        "selflink",
                        "",
                        "",
                        new LinkedHashMap<>()
                )),
                "date", 0
        );

        String expectedPlayernames = "player";
        String actualPlayernames = runWithOnePlayer.playernamesForLeaderboard();

        Assertions.assertEquals(expectedPlayernames, actualPlayernames);
    }

    @Test
    public void test_playernamesForLeaderboard_morePlayers() {
        RunStorage runWithMorePlayers = new RunStorage(
                List.of(
                        new PlayerStorage("player 1", "selflink 1", "", "", new LinkedHashMap<>()),
                        new PlayerStorage("player 2", "selflink 2", "", "", new LinkedHashMap<>())
                ),
                "date", 0
        );

        String expectedPlayernames = "player 1, ...";
        String actualPlayernames = runWithMorePlayers.playernamesForLeaderboard();

        Assertions.assertEquals(expectedPlayernames, actualPlayernames);
    }

    @Test
    public void test_prettyDateSubmitted_fromNull() {
        RunStorage runWithNullDate = new RunStorage(
                List.of(), null, 0
        );

        String expectedPrettyDate = "<no date found>";
        String actualPrettyDate = runWithNullDate.prettyDateSubmitted();

        Assertions.assertEquals(expectedPrettyDate, actualPrettyDate);
    }
}
