package edu.bsu.cs.records;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryStorageTest {
    @Test
    public void test_toString_perGame() {
        CategoryStorage category = new CategoryStorage("id", "name", "per-game");

        String expectedToString = "name";
        String actualToString = category.toString();

        Assertions.assertEquals(expectedToString, actualToString);
    }
    @Test
    public void test_toString_perLevel() {
        CategoryStorage category = new CategoryStorage("id", "name", "per-level");

        String expectedToString = "name*";
        String actualToString = category.toString();

        Assertions.assertEquals(expectedToString, actualToString);
    }
}
