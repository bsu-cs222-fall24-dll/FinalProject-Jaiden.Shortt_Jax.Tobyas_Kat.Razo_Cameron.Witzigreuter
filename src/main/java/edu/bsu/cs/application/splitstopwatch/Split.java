package edu.bsu.cs.application.splitstopwatch;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// Since the TableView that holds these splits is not yet implemented,
// there is no possible way to have used methods from this class.
@SuppressWarnings("unused")

public class Split {

    public Split(String timeTotal, String timeSince) {
        splitNameProperty().set("");
        timeTotalProperty().set(timeTotal);
        timeSinceProperty().set(timeSince);
    }


    private StringProperty splitName;
    public StringProperty splitNameProperty() {
        if (splitName == null) splitName = new SimpleStringProperty(this, "splitName");
        return splitName;
    }
    public String getSplitName() {
        return splitNameProperty().get();
    }

    private StringProperty timeTotal;
    public StringProperty timeTotalProperty() {
        if (timeTotal == null) timeTotal = new SimpleStringProperty(this, "timeTotal");
        return timeTotal;
    }
    public String getTimeTotal() {
        return timeTotalProperty().get();
    }

    private StringProperty timeSince;
    public StringProperty timeSinceProperty() {
        if (timeSince == null) timeSince = new SimpleStringProperty(this, "timeSince");
        return timeSince;
    }
    public String getTimeSince() {
        return timeSinceProperty().get();
    }

}
