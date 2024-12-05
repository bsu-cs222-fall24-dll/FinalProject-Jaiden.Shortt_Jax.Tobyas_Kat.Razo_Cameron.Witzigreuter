package edu.bsu.cs.application.splitstopwatch;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Split {

    private final long timestampMillis;

    public Split(long timestampMillis) {
        this.timestampMillis = timestampMillis;

        splitNameProperty().set("");
        timeTotalProperty().set(Stopwatch.getTimeElapsedFormatted(timestampMillis));
        timeSinceProperty().set(getTimeTotal());
    }
    public Split(long timestampMillis, Split previousSplit) {
        this.timestampMillis = timestampMillis;

        splitNameProperty().set("");
        timeTotalProperty().set(Stopwatch.getTimeElapsedFormatted(timestampMillis));
        timeSinceProperty().set(Stopwatch.getTimeElapsedFormatted(timestampMillis - previousSplit.timestampMillis));
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

    @Override
    public String toString() {
        return String.format("%s | millis: %d | total: %s | since previous: %s",
                (getSplitName().isEmpty()) ? "unnamed split" : getSplitName(),
                timestampMillis,
                getTimeTotal(),
                getTimeSince()
        );
    }

    public void setSplitName(String splitName) {
        splitNameProperty().set(splitName);
    }
}
