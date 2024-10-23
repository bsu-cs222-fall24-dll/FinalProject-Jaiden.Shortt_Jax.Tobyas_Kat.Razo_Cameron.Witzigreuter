package edu.bsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//test fails, but I'm not sure how else to taste it
public class DataStorageTest {
    @Test
    public void timestampConvertTest(){
        DataStorage dataStorage = new DataStorage("PT12M9.580S");
        String actualVal= dataStorage.getTimestamp();
        String expectedVal = "12:9:580";

        Assertions.assertEquals(expectedVal,actualVal);
    }
}
