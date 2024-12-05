package edu.bsu.cs.application;

import edu.bsu.cs.application.playerprofile.PlayerProfileController;
import edu.bsu.cs.records.*;
import edu.bsu.cs.webapihandlers.GameHandler;
import edu.bsu.cs.webapihandlers.LeaderboardHandler;
import edu.bsu.cs.webapihandlers.RunsListHandler;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SpeedrunStatsController {
    GameStorage activeGame;
    ObservableList<TableSpeedrunStorage> tableSpeedruns;

    @FXML private Label gameNameLabel;
    @FXML private TextField searchField;

    @FXML private ChoiceBox<CategoryStorage> categoryChoiceBox;
    @FXML private ChoiceBox<LevelStorage> levelChoiceBox;
    @FXML private CheckBox levelCheckbox;

    @FXML private Button runsByPlaceButton;
    @FXML private Button runsByDateButton;

    @FXML private TableView<TableSpeedrunStorage> leaderboardTable;
    @FXML private TableColumn<TableSpeedrunStorage, String> placeColumn;
    @FXML private TableColumn<TableSpeedrunStorage, String> usernameColumn;
    @FXML private TableColumn<TableSpeedrunStorage, String> dateColumn;
    @FXML private TableColumn<TableSpeedrunStorage, String> runTimeColumn;

    public void initialize() {
        tableSpeedruns = FXCollections.observableList(new ArrayList<>());
        leaderboardTable.setItems(tableSpeedruns);

        placeColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getPlace()));
        usernameColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getUsername()));
        dateColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getDate()));
        runTimeColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getRunTime()));
    }


    @FXML void searchGame() {
        String searchText = searchField.getText().trim();

        if (searchText.isEmpty())
            return;

        try {
            searchField.setDisable(true);

            activeGame = GameHandler.getGameData(searchText);
            gameNameLabel.setText(String.format("Current Game: %s", activeGame.name()));

            categoryChoiceBox.setDisable(false);
            levelCheckbox.setDisable(false);

            levelChoiceBox.getItems().clear();
            configureChoiceBoxes();

            runsByPlaceButton.setDisable(false);
            runsByDateButton.setDisable(false);

            searchField.setDisable(false);
            leaderboardTable.setDisable(true);
        }
        catch (Exception e) {
            searchField.setDisable(false);
            handleException(e);
        }
    }
    @FXML void configureChoiceBoxes() {
        if (activeGame.levels().isEmpty()) {
            levelCheckbox.setSelected(false);
            levelCheckbox.setDisable(true);
        }

        boolean shouldShowLevels = levelCheckbox.isSelected();
        levelChoiceBox.setDisable(!shouldShowLevels);
        categoryChoiceBox.getItems().clear();

        for (CategoryStorage category : activeGame.categories()) {
            if (shouldShowLevels && category.type().equals("per-level"))
                categoryChoiceBox.getItems().add(category);
            else if (!shouldShowLevels && category.type().equals("per-game"))
                categoryChoiceBox.getItems().add(category);
        }
        if (levelChoiceBox.getItems().isEmpty())
            levelChoiceBox.getItems().setAll(activeGame.levels());

        if (categoryChoiceBox.getValue() == null)
            categoryChoiceBox.setValue(categoryChoiceBox.getItems().getFirst());
        if (levelChoiceBox.getValue() == null && !activeGame.levels().isEmpty())
            levelChoiceBox.setValue(levelChoiceBox.getItems().getFirst());

        categoryChoiceBox.setValue(categoryChoiceBox.getItems().getFirst());
    }

    private void populateTableView(LeaderboardStorage leaderboard) {
        if (leaderboard == null)
            return;

        leaderboardTable.getItems().clear();

        for (int i = 0; i < leaderboard.runs().size(); i++)
            tableSpeedruns.add(new TableSpeedrunStorage(leaderboard, i));

        leaderboardTable.setItems(FXCollections.observableList(tableSpeedruns));
        leaderboardTable.setDisable(false);
    }

    @FXML void showTopLeaderboard() {
        populateTableView(parseAndGetLeaderboard(false));
    }
    @FXML void showRecentRunsAsLeaderboard() {
        populateTableView(parseAndGetLeaderboard(true));
    }

    private LeaderboardStorage parseAndGetLeaderboard(boolean justRuns) {
        try {
            CategoryStorage selectedCategory = categoryChoiceBox.getValue();
            LevelStorage selectedLevel = (levelCheckbox.isSelected())
                    ? levelChoiceBox.getValue()
                    : null;

            if (!justRuns)
                return LeaderboardHandler.getLeaderboard(activeGame, selectedCategory, selectedLevel, 20);
            else
                return RunsListHandler.getRunsListWithLeaderboardParameters(activeGame, selectedCategory, selectedLevel, 20, "orderby=submitted&direction=desc");
        }
        catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    private void handleException(Exception e) {
        e.printStackTrace(System.err);
        e = modifyException(e);

        Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
        exceptionAlert.setTitle("An error occurred!");
        exceptionAlert.setHeaderText(e.getClass().getSimpleName());
        exceptionAlert.setContentText(e.getMessage());
        exceptionAlert.setHeight(500);

        exceptionAlert.showAndWait();
    }
    private Exception modifyException(Exception e) {
        // This inspection is disabled for the same reason as the disabled inspection in CLI.java at line 143:9.
        // In short, this statement causes more warnings and makes less readable sense as a switch statement.

        //noinspection IfCanBeSwitch
        if (e instanceof FileNotFoundException)
            return new FileNotFoundException(String.format("No data was found for:%n%n%s", e.getMessage()));

        else if (e instanceof UnknownHostException)
            return new UnknownHostException("Please check your internet connection.");

        else if (e instanceof IOException)
            return new IOException("An unexpected internet problem occurred.");

        else
            return e;
    }


    @FXML void openPlayerProfileWithSelectedCell(MouseEvent event) {
        if (event.getClickCount() == 2)
            openPlayerProfile(leaderboardTable.getSelectionModel().getSelectedItem().getPlayer());
    }
    private void openPlayerProfile(PlayerStorage player){
        try {
            FXMLLoader playerProfileLoader = new FXMLLoader(getClass().getResource("playerprofile/playerprofile-view.fxml"));
            Parent root = playerProfileLoader.load();

            Stage playerProfileStage = new Stage();
            playerProfileStage.setTitle("Profile");
            playerProfileStage.setScene(new Scene(root));
            playerProfileStage.show();

            if (playerProfileLoader.getController() instanceof PlayerProfileController)
                ((PlayerProfileController) playerProfileLoader.getController()).inputPlayerInformation(player);
                
        } catch (IOException e) {
           handleException(e);
        }
    }


    @FXML void openTimerTool() {
        try {
            FXMLLoader splitStopwatchLoader = new FXMLLoader(getClass().getResource("splitstopwatch/splitstopwatch-view.fxml"));
            Parent root = splitStopwatchLoader.load();

            Stage splitStopwatchStage = new Stage();
            splitStopwatchStage.setTitle("Split Stopwatch");
            splitStopwatchStage.setScene(new Scene(root));
            splitStopwatchStage.show();
        }
        catch (IOException e) {
            handleException(e);
        }
    }


    @FXML void exit() {
        System.exit(0);
    }
}
