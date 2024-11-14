package edu.bsu.cs.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LeaderboardApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("leaderboard-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Speedrun Stats v0.2.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
