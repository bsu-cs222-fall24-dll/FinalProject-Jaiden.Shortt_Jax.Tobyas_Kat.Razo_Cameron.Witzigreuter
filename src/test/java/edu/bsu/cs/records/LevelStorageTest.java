package edu.bsu.cs.records;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LevelStorageTest {
    @Test
    public void test_toString() {
        LevelStorage level = new LevelStorage("id", "name");

        String expectedToString = "name";
        String actualToString = level.toString();

        Assertions.assertEquals(expectedToString, actualToString);
    }
}
