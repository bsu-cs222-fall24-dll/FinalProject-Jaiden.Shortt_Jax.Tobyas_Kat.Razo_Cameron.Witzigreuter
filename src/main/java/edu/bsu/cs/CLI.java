package edu.bsu.cs;

import edu.bsu.cs.records.*;
import edu.bsu.cs.webapihandlers.GameHandler;
import edu.bsu.cs.webapihandlers.LeaderboardHandler;
import edu.bsu.cs.webapihandlers.RunsListHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private static final Scanner consoleScanner = new Scanner(System.in);

    private static GameStorage activeGame;
    private static final List<CategoryStorage> categoriesToChooseFrom = new ArrayList<>();
    private static CategoryStorage activeCategory;
    private static LevelStorage activeLevel;
    private static LeaderboardStorage leaderboard;

    public static void main(String[] args) {
        activeGame = activateGame();
        activateCategoryAndLevelForLeaderboard();

        boolean getRunsByPlace = promptForAndGetLeaderboardType();

        getAndPrintLeaderboard(getRunsByPlace);

        consoleScanner.close();
        System.exit(0);
    }


    private static GameStorage activateGame() {
        String userInputGameSlug = promptForAndGetGameFromUserInput();
        return searchGame(userInputGameSlug);
    }

    private static String promptForAndGetGameFromUserInput() {
        System.out.printf("Enter a game ID or abbreviation.%n");
        System.out.printf("(ex, 'sms or '‘v1pxjz68’ for Super Mario Sunshine):%n>> ");
        return consoleScanner.nextLine();
    }
    private static GameStorage searchGame(String gameIdOrAbv) {
        try {
            System.out.print("Searching... ");

            GameStorage toReturn = GameHandler.getGameData(gameIdOrAbv);
            System.out.printf("Game Found! [%s]%n", toReturn.name());

            return toReturn;
        }
        catch (FileNotFoundException searchFoundNoGameException) {
            System.err.println("No game found. Please try again.");
            return activateGame();
        }
        catch (IOException otherWebInteractionException) {
            System.err.println("An unexpected problem occurred. Please try again.");
            return activateGame();
        }
    }


    private static void activateCategoryAndLevelForLeaderboard() {
        boolean useLevelsAndPerLevelCategories = promptForAndGetCategoryType();

        updateCategoriesToChooseFrom(useLevelsAndPerLevelCategories);
        printCategoriesToChooseFrom();

        activeCategory = havePlayerChooseCategory();

        if (useLevelsAndPerLevelCategories)
            printLevelsToChooseFrom();
        activeLevel = (useLevelsAndPerLevelCategories)
                ? havePlayerChooseLevel()
                : null;
    }

    private static boolean promptForAndGetCategoryType() {
        System.out.printf("%nWould you like to use per-game categories or per-level categories?%n");
        System.out.printf("Options: 'game' 'level' / 'g' 'l'%n>> ");

        String userAnswer = consoleScanner.nextLine().toLowerCase().trim();

        if (userAnswer.equals("game") || userAnswer.equals("g"))
            return false;
        else if (userAnswer.equals("level") || userAnswer.equals("l"))
            return true;

        System.err.println("Input is not a valid option. Please try again.");
        return promptForAndGetCategoryType();
    }
    private static void updateCategoriesToChooseFrom(boolean useLevels) {
        categoriesToChooseFrom.clear();

        for (CategoryStorage category : activeGame.categories()) {
            if (useLevels && category.type().equals("per-level"))
                categoriesToChooseFrom.add(category);

            else if (!useLevels && category.type().equals("per-game"))
                categoriesToChooseFrom.add(category);
        }
    }

    private static void printCategoriesToChooseFrom() {
        System.out.println("-".repeat(50));

        System.out.printf("Showing %s categories for %s:%n",
                categoriesToChooseFrom.getFirst().type(),
                activeGame.name()
        );

        System.out.println("-".repeat(50));

        for (int i = 0; i < categoriesToChooseFrom.size(); i++)
            System.out.printf("%3d. %s%n", i + 1, categoriesToChooseFrom.get(i).name());

        System.out.println("-".repeat(50));
    }
    private static CategoryStorage havePlayerChooseCategory() {
        try {
            System.out.printf("Please enter the number associated with a category to choose that category.%n>> ");
            int categoryIndex = Integer.parseInt(consoleScanner.nextLine()) - 1;

            return categoriesToChooseFrom.get(categoryIndex);
        }
        catch (NumberFormatException categoryIndexCouldNotBeParsedAsIntException) {
            System.err.println("Input was not a number. Please try again.");
            return havePlayerChooseCategory();
        }
        catch (IndexOutOfBoundsException numberWasTooHighOrLowException) {
            System.err.println("That number is not in the list. Please try again.");
            return havePlayerChooseCategory();
        }
    }

    private static void printLevelsToChooseFrom() {
        System.out.println("-".repeat(50));

        System.out.printf("Showing levels for %s:%n",
                activeGame.name()
        );

        System.out.println("-".repeat(50));

        for (int i = 0; i < activeGame.levels().size(); i++)
            System.out.printf("%3d. %s%n", i + 1, activeGame.levels().get(i).name());

        System.out.println("-".repeat(50));
    }
    private static LevelStorage havePlayerChooseLevel() {
        try {
            System.out.printf("Please enter the number associated with a level to choose that level.%n>> ");
            int levelIndex = Integer.parseInt(consoleScanner.nextLine()) - 1;

            return activeGame.levels().get(levelIndex);
        }
        catch (NumberFormatException categoryIndexCouldNotBeParsedAsIntException) {
            System.err.println("Input was not a number. Please try again.");
            return havePlayerChooseLevel();
        }
        catch (IndexOutOfBoundsException numberWasTooHighOrLowException) {
            System.err.println("That number is not in the list. Please try again.");
            return havePlayerChooseLevel();
        }
    }


    private static boolean promptForAndGetLeaderboardType() {
        System.out.println("Would you like to sort runs by place or by date??");
        System.out.printf("Options: 'place' 'date' / 'p' 'd'%n>> ");

        String userAnswer = consoleScanner.nextLine().toLowerCase().trim();

        if (userAnswer.equals("place") || userAnswer.equals("p"))
            return true;
        else if (userAnswer.equals("date") || userAnswer.equals("d"))
            return false;

        System.err.println("Input is not a valid option. Please try again.");
        return promptForAndGetLeaderboardType();
    }


    private static void getAndPrintLeaderboard(boolean byPlace) {
        try {
            if (byPlace)
                leaderboard = LeaderboardHandler.getLeaderboard(activeGame, activeCategory, activeLevel, 20);
            else
                leaderboard = RunsListHandler.getRunsListWithLeaderboardParameters(activeGame, activeCategory, activeLevel, 20,
                        "orderby=submitted&direction=desc");

            printLeaderboard();
        }
        catch (IOException e) {
            System.err.printf("An unexpected %s ocurred while getting the leaderboard.%n",
                    e.getClass().getSimpleName());
            System.out.println("Press enter to try again, 'q' to abort the program, or 'see' to get a stacktrace.");

            System.out.print(">> ");
            String userInput = consoleScanner.nextLine();

            if (userInput.equals("q")) {
                consoleScanner.close();
                System.exit(1);
            }
            else if (userInput.equals("see"))
                e.printStackTrace(System.err);
            else
                getAndPrintLeaderboard(byPlace);
        }
    }
    private static void printLeaderboard() {
        System.out.printf("%nLeaderboard for: %s [%s]%n%s%n", activeGame.name(), activeCategory.name(), "-".repeat(56));

        for (int i = 0; i < leaderboard.runs().size(); i++) {
            RunStorage thisRun = leaderboard.runs().get(i);

            System.out.printf(
                    "#%-3s %-21s %-18s %s%n",

                    leaderboard.runPlaces().get(i),
                    thisRun.playernamesForLeaderboard(),
                    thisRun.prettyDateSubmitted(),
                    thisRun.formattedRunTime()
            );
        }

        System.out.println("-".repeat(56));
    }
}