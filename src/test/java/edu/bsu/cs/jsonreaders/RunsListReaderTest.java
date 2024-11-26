package edu.bsu.cs.jsonreaders;

import edu.bsu.cs.records.PlayerStorage;
import edu.bsu.cs.records.RunStorage;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RunsListReaderTest {
    @BeforeAll
    public static void initializeRunsListReader() throws IOException {
        smsRunsListReader = new RunsListReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/jsonreaders/RunsListReader/sms-anypercent-runs-by-date.json"), StandardCharsets.UTF_8)
        );
        pseudoRunsListReader = new RunsListReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/jsonreaders/RunsListReader/fake-runslist-for-players.json"), StandardCharsets.UTF_8)
        );
    }
    static RunsListReader smsRunsListReader, pseudoRunsListReader;

    @Test
    public void test_createRunsList() {
        List<RunStorage> expectedRunsList = getRunsListForTest();
        List<RunStorage> actualRunsList = smsRunsListReader.createRunsList();

        Assertions.assertEquals(expectedRunsList, actualRunsList);
    }
    private List<RunStorage> getRunsListForTest() {
        return List.of(
                new RunStorage(
                        List.of(new PlayerStorage("Nonag", "https://www.speedrun.com/api/v1/users/xkm3lk7j")),
                        "2024-11-17T12:04:01Z", "PT1H25M41S"
                ),
                new RunStorage(
                        List.of(new PlayerStorage("shionkun", "https://www.speedrun.com/api/v1/users/j40p3pl8")),
                        "2024-11-17T10:45:52Z", "PT1H14M48S"
                ),
                new RunStorage(
                        List.of(new PlayerStorage("Miton", "https://www.speedrun.com/api/v1/users/qj23lpxk")),
                        "2024-11-17T05:16:37Z", "PT23M48S"
                )
        );
    }

    @Test
    public void test_createRunsList_forCoverage() {
        List<RunStorage> expectedRunsList = getRunsListForCoverageTest();
        List<RunStorage> actualRunsList = pseudoRunsListReader.createRunsList();

        Assertions.assertEquals(expectedRunsList, actualRunsList);
    }
    private List<RunStorage> getRunsListForCoverageTest() {
        return List.of(
                new RunStorage(
                        List.of(new PlayerStorage("user", "run1userselflink")),
                        "submitted", "run1time"
                ),
                new RunStorage(
                        List.of(new PlayerStorage("guest1", "run2guestselflink")),
                        "date", "run2time"
                ),
                new RunStorage(
                        List.of(new PlayerStorage("guest2", "run3guestselflink")),
                        "verify-date", "run3time"
                ),
                new RunStorage(
                        List.of(new PlayerStorage("guest3", "run4guestselflink")),
                        null, "run4time"
                )
        );
    }
}
