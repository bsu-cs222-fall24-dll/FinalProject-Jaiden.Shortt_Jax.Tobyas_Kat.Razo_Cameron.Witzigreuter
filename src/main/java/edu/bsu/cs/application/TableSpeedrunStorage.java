package edu.bsu.cs.application;

import edu.bsu.cs.records.LeaderboardStorage;
import edu.bsu.cs.records.PlayerStorage;
import edu.bsu.cs.records.RunStorage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableSpeedrunStorage {
    private final RunStorage leaderboardRun;

    public TableSpeedrunStorage(LeaderboardStorage leaderboard, int indexOfRun) {
        leaderboardRun = leaderboard.runs().get(indexOfRun);

        placeProperty().set(String.valueOf(leaderboard.runPlaces().get(indexOfRun)));
        usernameProperty().set(leaderboardRun.playernamesForLeaderboard());
        dateProperty().set(leaderboardRun.prettyDateSubmitted());
        runTimeProperty().set(String.valueOf(leaderboardRun.primaryRunTime()));
    }

    public PlayerStorage getPlayer() {
        return leaderboardRun.players().getFirst();
    }



    private StringProperty place;
    public StringProperty placeProperty() {
        if (place == null) place = new SimpleStringProperty(this, "place");
        return place;
    }
    public String getPlace() { return placeProperty().get(); }

    private StringProperty username;
    public StringProperty usernameProperty() {
        if (username == null) username = new SimpleStringProperty(this, "playersName");
        return username;
    }
    public String getUsername() { return usernameProperty().get(); }

    private StringProperty runTime;
    public StringProperty runTimeProperty() {
        if (runTime == null) runTime = new SimpleStringProperty(this, "runTime");
        return runTime;
    }
    public String getRunTime() { return runTimeProperty().get(); }

    private StringProperty dateAgo;
    public StringProperty dateProperty() {
        if (dateAgo == null) dateAgo = new SimpleStringProperty(this, "dateAgo");
        return dateAgo;
    }
    public String getDate() { return dateProperty().get(); }

}
