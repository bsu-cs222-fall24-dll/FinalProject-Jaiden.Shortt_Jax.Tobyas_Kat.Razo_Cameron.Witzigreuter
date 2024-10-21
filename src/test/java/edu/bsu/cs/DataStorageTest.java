package edu.bsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DataStorageTest {
    @Test
    public void test_DataStorage_toString(){

        //Not sure if we're considering categories as shown in the Final Project sample UI.
        //speedrun.com Star Fetchers might include Pilot, Episode 1 Demo, and Escape From Pork Belly as categories? to choose from in the sample UI.

        String redirectForGame = "Star Fetchers";

        //top 10 times, names, and dates, per category
        List<String> timesForGame = Arrays.asList(
                "PT11M31.850S",
                "PT11M38.055S",
                "PT11M46.130S",
                "PT12M9.430S",
                "PT12M29.025S",
                "PT12M30.960S",
                "PT12M34.960S",
                "PT12M40.010S",
                "PT12M54.360S",
                "PT12M57.095S"
        );
        List<String> usersForGame = Arrays.asList(
                "friesfrench",
                "RatboyInsanity",
                "cardboardhed",
                "Marcuszzz",
                "dayne",
                "Reikagari",
                "osuepo",
                "NinjaNugget",
                "QuixoticD2",
                "FrostiidFlakes"
        );
        List<String> datesForGame = Arrays.asList(
                "2021-11-24",
                "2021-11-10",
                "2021-07-25",
                "2021-07-25",
                "2021-07-19",
                "2020-03-01",
                "2020-02-22",
                "2023-01-12",
                "2020-02-18",
                "2022-01-15"
        );
        String actualDataStorageString = new DataStorage(redirectForGame, usersForGame,timesForGame,datesForGame).toString();

        String expectedDataStorageString = String.format("Redirected to Star Fetchers%n"+
                "friesfrench  PT11M31.850S  2021-11-24%n"+
                "RatboyInsanity  PT11M38.055S  2021-11-10%n"+
                "cardboardhed  PT11M46.130S  2021-07-25%n"+
                "Marcuszzz  PT12M9.430S  2021-07-25%n"+
                "dayne  PT12M29.025S  2021-07-19%n"+
                "Reikagari  PT12M30.960S  2020-03-01%n"+
                "osuepo  PT12M34.960S  2020-02-22%n"+
                "NinjaNugget  PT12M40.010S  2023-01-12%n"+
                "QuixoticD2  PT12M54.360S  2020-02-18%n"+
                "FrostiidFlakes  PT12M57.095S  2022-01-15%n"
        );
        Assertions.assertEquals(expectedDataStorageString, actualDataStorageString);
    }

    @Test
    public void test_exampleDataStorage_NoRedirect(){
        String redirectForGame = "";
        List<String> timesForGame = Arrays.asList(
                "PT11M31.850S",
                "PT11M38.055S",
                "PT11M46.130S",
                "PT12M9.430S",
                "PT12M29.025S",
                "PT12M30.960S",
                "PT12M34.960S",
                "PT12M40.010S",
                "PT12M54.360S",
                "PT12M57.095S"
        );
        List<String> usersForGame = Arrays.asList(
                "friesfrench",
                "RatboyInsanity",
                "cardboardhed",
                "Marcuszzz",
                "dayne",
                "Reikagari",
                "osuepo",
                "NinjaNugget",
                "QuixoticD2",
                "FrostiidFlakes"
        );
        List<String> datesForGame = Arrays.asList(
                "2021-11-24",
                "2021-11-10",
                "2021-07-25",
                "2021-07-25",
                "2021-07-19",
                "2020-03-01",
                "2020-02-22",
                "2023-01-12",
                "2020-02-18",
                "2022-01-15"
        );
        String actualDataStorageString = new DataStorage(redirectForGame, usersForGame,timesForGame,datesForGame).toString();

        String expectedDataStorageString = String.format(
                "friesfrench  PT11M31.850S  2021-11-24%n"+
                "RatboyInsanity  PT11M38.055S  2021-11-10%n"+
                "cardboardhed  PT11M46.130S  2021-07-25%n"+
                "Marcuszzz  PT12M9.430S  2021-07-25%n"+
                "dayne  PT12M29.025S  2021-07-19%n"+
                "Reikagari  PT12M30.960S  2020-03-01%n"+
                "osuepo  PT12M34.960S  2020-02-22%n"+
                "NinjaNugget  PT12M40.010S  2023-01-12%n"+
                "QuixoticD2  PT12M54.360S  2020-02-18%n"+
                "FrostiidFlakes  PT12M57.095S  2022-01-15%n"
        );
        Assertions.assertEquals(expectedDataStorageString, actualDataStorageString);
    }
}
