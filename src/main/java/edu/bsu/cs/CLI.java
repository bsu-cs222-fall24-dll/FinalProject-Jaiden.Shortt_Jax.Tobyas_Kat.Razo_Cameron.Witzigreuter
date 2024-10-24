package edu.bsu.cs;

import edu.bsu.cs.records.CategoryStorage;
import edu.bsu.cs.records.GameStorage;
import edu.bsu.cs.records.LeaderboardStorage;
import edu.bsu.cs.records.RunStorage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CLI {
    public static final Scanner consoleScanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.printf("%nPlease enter a game id or abbreviation. (Ex. 'sms', or 'v1pxjz68')%n>> ");
        String gameNameInput = consoleScanner.nextLine();

        System.out.printf("%nSearching... ");
        GameStorage game = WebApiHandler.getGameData(gameNameInput);
        System.out.printf("Game Found! [%s]%n%n", game.name());

        List<CategoryStorage> categoryList = WebApiHandler.getCategoryData(game);
        System.out.printf("Enter # for desired Category.%n%s%n", "-".repeat(20));
        for (int i = 1; i <= categoryList.size(); i++)
            System.out.printf("* %-3s %s%n", String.format("%d.", i), categoryList.get(i - 1));
        System.out.println("-".repeat(20));

        System.out.print(">> ");
        int categoryIndex = Integer.parseInt(consoleScanner.nextLine()) - 1;

        LeaderboardStorage leaderboard = WebApiHandler.getLeaderboardData(categoryList.get(categoryIndex), 20);
        System.out.printf("%nLeaderboard for: %s%n%s%n", categoryList.get(categoryIndex).name(), "-".repeat(20));
        for (Map.Entry<Integer, RunStorage> entry : leaderboard.runs().entrySet())
            System.out.printf("#%-3s %s%n", String.format("%d.", entry.getKey()), entry.getValue());
        System.out.println("-".repeat(20));

        consoleScanner.close();
        System.exit(0);
    }
}