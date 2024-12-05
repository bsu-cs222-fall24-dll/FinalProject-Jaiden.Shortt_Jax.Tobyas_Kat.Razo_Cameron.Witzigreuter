package edu.bsu.cs.application.splitstopwatch;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch {
    private final Timer timer = new Timer();
    private TimerTask incrementMillisTask;

    private long timeElapsedMillis = 0;

    private Label stopwatchLabel;


    public void startStopwatch(Label stopwatchLabel) {
        incrementMillisTask = new TimerTask() {
            @Override
            public void run() {
                increment();
            }
        };

        this.stopwatchLabel = stopwatchLabel;

        timer.scheduleAtFixedRate(incrementMillisTask, 0, 1);
    }

    private void increment() {
        timeElapsedMillis++;

        String timeForLabel = getTimeElapsedFormatted();

        Platform.runLater(() -> stopwatchLabel.setText(timeForLabel));
    }


    public void stopStopwatch() {
        if (incrementMillisTask != null)
            incrementMillisTask.cancel();
    }

    public void resetStopwatch() {
        stopStopwatch();

        timeElapsedMillis = 0;
        stopwatchLabel.setText("-:--.---");
    }


    public long getTimeElapsedMillis() {
        return timeElapsedMillis;
    }

    public String getTimeElapsedFormatted() {
        return getTimeElapsedFormatted(timeElapsedMillis);
    }
    public static String getTimeElapsedFormatted(long milliseconds) {
        long millisElapsed, secondsElapsed, minutesElapsed;
        millisElapsed = milliseconds % 1000;
        secondsElapsed = (milliseconds / 1000) % 60;
        minutesElapsed = milliseconds / (1000 * 60);

        return String.format("%d:%02d.%03d",
                minutesElapsed, secondsElapsed, millisElapsed);
    }
}
