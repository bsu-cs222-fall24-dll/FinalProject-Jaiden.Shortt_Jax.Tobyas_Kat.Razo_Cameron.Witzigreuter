package edu.bsu.cs;

import edu.bsu.cs.records.CategoryStorage;
import edu.bsu.cs.records.GameStorage;
import edu.bsu.cs.records.LeaderboardStorage;
import edu.bsu.cs.records.RunStorage;
import edu.bsu.cs.webapihandlers.CategoryHandler;
import edu.bsu.cs.webapihandlers.GameHandler;
import edu.bsu.cs.webapihandlers.LeaderboardHandler;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private static final Scanner consoleScanner = new Scanner(System.in);

    private static GameStorage chosenGame;
    private static List<CategoryStorage> categoryList;
    private static CategoryStorage chosenCategory;
    private static LeaderboardStorage leaderboard;

    private static final boolean LEVELS_ARE_SUPPORTED = false;

    public static void main(String[] args) {
        printGamePromptAndGetChoice();
        printCategoryMenuAndGetChoice();
        getAndPrintLeaderboard();

        consoleScanner.close();
        System.exit(0);
    }


    private static void printGamePromptAndGetChoice() {
        System.out.printf("%nPlease enter a game id or abbreviation. (Ex. 'sms', or 'v1pxjz68')%n");
        getGameChoice();
    }
    private static void getGameChoice() {
        String gameNameInput;
        System.out.print(">> ");

        try {
            gameNameInput = consoleScanner.nextLine();

            System.out.printf("%nSearching... ");
            chosenGame = GameHandler.getGameData(gameNameInput);
            System.out.printf("Game Found! [%s]%n%n", chosenGame.name());
        }
        catch (Exception e) {
            handleError(e);

            System.out.println("Please try again.");
            getGameChoice();
        }
    }

    private static void printCategoryMenuAndGetChoice() {
        try {
            categoryList = CategoryHandler.getCategoryData(chosenGame);

            if (!LEVELS_ARE_SUPPORTED)
                for (int i = categoryList.size() - 1; i >= 0; i--)
                    if (categoryList.get(i).type().equals("per-level"))
                        categoryList.remove(i);

            printCategoryMenu();
            getCategoryChoice();
        }
        catch (Exception e) {
            handleError(e);

            System.out.println("Please try again.");
            printCategoryMenuAndGetChoice();
        }
    }
    private static void printCategoryMenu() {
        System.out.printf("Enter # for desired Category.%n%s%n", "-".repeat(20));

        for (int i = 1; i <= categoryList.size(); i++)
            System.out.printf("* %-3s %s%n", String.format("%d.", i), categoryList.get(i - 1));

        System.out.println("-".repeat(20));
    }
    private static void getCategoryChoice() {
        int categoryChoiceInput;
        System.out.print(">> ");

        try {
            categoryChoiceInput = Integer.parseInt(consoleScanner.nextLine());
            chosenCategory = categoryList.get(categoryChoiceInput - 1);
        }
        catch (Exception e) {
            handleError(e);

            System.out.println("Please try again.");
            getCategoryChoice();
        }
    }

    private static void getAndPrintLeaderboard() {
        try {
            leaderboard = LeaderboardHandler.getLeaderboardData(chosenGame, chosenCategory, null, 20);
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
                getAndPrintLeaderboard();
        }
    }
    private static void printLeaderboard() {
        System.out.printf("%nLeaderboard for: %s [%s]%n%s%n", chosenGame.name(), chosenCategory.name(), "-".repeat(56));

        for (int i = 0; i < leaderboard.runs().size(); i++) {
            RunStorage thisRun = leaderboard.runs().get(i);

            System.out.printf(
                    "#%-3s %-21s %-18s %s%n",

                    leaderboard.runPlaces().get(i),
                    thisRun.playernamesForLeaderboard(),
                    thisRun.prettyDateSubmitted(),
                    thisRun.primaryRunTime()
            );
        }

        System.out.println("-".repeat(56));
    }


    private static void handleError(Exception e) {
        String className = e.getClass().getSimpleName();

        /* As a switch statement, the following if statement:
         * - ignores pattern variables (creating four warnings)
         * - misinterprets vertical and horizontal formatting
         * - becomes less readable
         *
         * In light of these conflicts, the warning is hereby suppressed. */

        //noinspection IfCanBeSwitch
        if (e instanceof UnknownHostException) {
            System.err.printf("An %s occurred. Please ensure your device is connected to the internet.%n", className);
        }
        else if (e instanceof IOException) {
            System.err.printf("An %s occurred. The web destination may have been incorrect.%n", className);
        }

        else if (e instanceof NumberFormatException) {
            System.err.printf("That is not an integer.%n");
        }
        else if (e instanceof IndexOutOfBoundsException) {
            System.err.printf("That number is not in the category menu.%n");
        }

        else {
            System.err.printf("An unexpected %s occurred. Please see the stacktrace for more.%n", className);

            System.out.print("Press enter to continue...");
            consoleScanner.nextLine();

            System.err.println(Arrays.toString(e.getStackTrace()));
            System.out.print("Press enter to continue...");
            consoleScanner.nextLine();
        }

    }
}