package edu.bsu.cs.application;

import edu.bsu.cs.records.RunStorage;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableSpeedrunStorage {

    public TableSpeedrunStorage(RunStorage tableSpeedrun) {
        placeProperty().set(tableSpeedrun.place());
        usernameProperty().set(tableSpeedrun.playernamesForLeaderboard());
        dateProperty().set(tableSpeedrun.prettyDateSubmitted());
        runTimeProperty().set(tableSpeedrun.primaryRunTime());
    }


    private IntegerProperty place;
    public IntegerProperty placeProperty() {
        if (place == null) place = new SimpleIntegerProperty(this, "place");
        return place;
    }
    public int getPlace() { return placeProperty().get(); }

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
