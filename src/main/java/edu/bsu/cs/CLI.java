package edu.bsu.cs;

import edu.bsu.cs.records.CategoryStorage;
import edu.bsu.cs.records.GameStorage;
import edu.bsu.cs.records.LeaderboardStorage;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private static final Scanner consoleScanner = new Scanner(System.in);

    private static GameStorage chosenGame;
    private static List<CategoryStorage> categoryList;
    private static CategoryStorage chosenCategory;
    private static LeaderboardStorage leaderboard;

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
            chosenGame = WebApiHandler.getGameData(gameNameInput);
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
            categoryList = WebApiHandler.getCategoryData(chosenGame);

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
            leaderboard = WebApiHandler.getLeaderboardData(chosenCategory, 20);
            printLeaderboard();
        }
        catch (IOException e) {
            System.err.printf("An unexpected %s ocurred while getting the leaderboard.%n",
                    e.getClass().getSimpleName());
            System.out.println("Press enter to try again, or q to abort the program.");

            System.out.print(">> ");
            String userInput = consoleScanner.nextLine();

            if (!userInput.equals("q"))
                getAndPrintLeaderboard();
        }
    }
    private static void printLeaderboard() {
        System.out.printf("%nLeaderboard for: %s%n%s%n", chosenCategory.name(), "-".repeat(20));

        leaderboard.runs().forEach((key, value) -> System.out.printf(
                "#%-3s %-61s %-16s %s%n",

                key,
                value.playerLinks(),
                new PrettyTime().format(Instant.parse(value.date_submitted())),
                value.primaryRunTime()
        ));

        System.out.println("-".repeat(20));
    }


    private static void handleError(Exception e) {
        if (e instanceof UnknownHostException) {
            System.err.printf("An %s occurred. Please ensure your device is connected to the internet.%n",
                    e.getClass().getSimpleName());
        }
        else if (e instanceof IOException) {
            System.out.println();
        }

        else if (e instanceof NumberFormatException) {
            System.err.println("That is not an integer.");
        }
        else if (e instanceof IndexOutOfBoundsException) {
            System.err.println("That number is not in the category menu.");
        }

        else {
            System.err.printf("An unexpected %s occurred. Please see the stacktrace for more.%n",
                    e.getClass().getSimpleName());

            System.out.print("Press enter to continue...");
            consoleScanner.nextLine();

            System.err.println(Arrays.toString(e.getStackTrace()));
            System.out.print("Press enter to continue...");
            consoleScanner.nextLine();
        }

    }
}