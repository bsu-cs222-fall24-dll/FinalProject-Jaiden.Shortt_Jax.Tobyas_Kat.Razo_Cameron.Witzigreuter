package edu.bsu.cs.application;

import edu.bsu.cs.WebApiHandler;
import edu.bsu.cs.records.CategoryStorage;
import edu.bsu.cs.records.GameStorage;
import edu.bsu.cs.records.LeaderboardStorage;
import edu.bsu.cs.records.LevelStorage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardController {
    GameStorage activeGame;
    List<CategoryStorage> activeCategories;
    List<LevelStorage> activeLevels;
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

            activeGame = WebApiHandler.getGameData(searchText);
            gameNameLabel.setText(String.format("Current Game: %s", activeGame.name()));

            activeCategories = WebApiHandler.getCategoryData(activeGame);
            categoryChoiceBox.setDisable(false);

            activeLevels = WebApiHandler.getLevelData(activeGame);
            levelChoiceBox.getItems().setAll(activeLevels);
            if (!activeLevels.isEmpty())
                levelChoiceBox.setValue(levelChoiceBox.getItems().getFirst());
            levelCheckbox.setDisable(false);

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
        boolean shouldShowLevels = levelCheckbox.isSelected();
        levelChoiceBox.setDisable(!shouldShowLevels);
        categoryChoiceBox.getItems().clear();

        for (CategoryStorage activeCategory : activeCategories) {
            if (shouldShowLevels && activeCategory.type().equals("per-level"))
                categoryChoiceBox.getItems().add(activeCategory);
            else if (!shouldShowLevels && activeCategory.type().equals("per-game"))
                categoryChoiceBox.getItems().add(activeCategory);
        }

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
                return WebApiHandler.getLeaderboardData(activeGame, selectedCategory, selectedLevel, 20);
            else
                return WebApiHandler.getLeaderboardData(activeGame, selectedCategory, selectedLevel, 20, "orderby=submitted&direction=desc");
        }
        catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    private void handleException(Exception e) {
        e = modifyException(e);

        Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
        exceptionAlert.setTitle("An error occurred!");
        exceptionAlert.setHeaderText(e.getClass().getSimpleName());
        exceptionAlert.setContentText(e.getMessage());

        e.printStackTrace(System.err);
        exceptionAlert.showAndWait();
    }
    private Exception modifyException(Exception e) {
        // This inspection is disabled for the same reason as the disabled inspection in CLI.java at line 143:9.
        // In short, this statement causes more warnings and makes less readable sense as a switch statement.

        //noinspection IfCanBeSwitch
        if (e instanceof FileNotFoundException)
            return new FileNotFoundException(String.format("The following link is invalid: %s", e.getMessage()));

        else if (e instanceof UnknownHostException)
            return new UnknownHostException("Please check your internet connection.");

        else if (e instanceof IOException)
            return new IOException("An unexpected internet problem occurred.");

        else
            return e;
    }
}
