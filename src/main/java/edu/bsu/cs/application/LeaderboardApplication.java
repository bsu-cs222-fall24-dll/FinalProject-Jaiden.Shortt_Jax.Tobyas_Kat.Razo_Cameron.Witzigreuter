package edu.bsu.cs.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class LeaderboardApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Scene scene = new Scene(new FXMLLoader().load(new FileInputStream("src/main/resources/edu.bsu.cs/application/leaderboard-view.fxml")));
        primaryStage.setTitle("Leaderboard v0.2.0a");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
