package edu.bsu.cs.application.splitstopwatch;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.ArrayList;

public class SplitStopwatchController {
    private final Stopwatch stopwatch = new Stopwatch();
    private ObservableList<Split> listOfSplitsForTable;

    @FXML private Label stopwatchTimeLabel;

    @FXML private TableView<Split> splitsTable;
    @FXML private TableColumn<Split, String> splitsNameColumn;
    @FXML private TableColumn<Split, String> splitsTimeTotalColumn;
    @FXML private TableColumn<Split, String> splitsTimeSinceColumn;

    public void initialize() {
        listOfSplitsForTable = FXCollections.observableList(new ArrayList<>());

        stopwatchTimeLabel.setText("-:--.---");

        splitsTable.getItems().clear();
        splitsNameColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getSplitName()));
        splitsNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        splitsTimeTotalColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getTimeTotal()));
        splitsTimeSinceColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getTimeSince()));
    }

    @FXML void startStopwatchAction() {
        stopwatch.startStopwatch(stopwatchTimeLabel);
    }
    @FXML void stopStopwatchAction() {
        stopwatch.stopStopwatch();
    }

    @FXML void addNewSplitAction() {
        addSplitToList();
        splitsTable.setItems(FXCollections.observableList(listOfSplitsForTable));
    }
    private void addSplitToList() {
        long currentTimestamp = stopwatch.getTimeElapsedMillis();

        Split splitToAdd = (listOfSplitsForTable.isEmpty())
                ? new Split(currentTimestamp)
                : new Split(currentTimestamp, listOfSplitsForTable.getLast());

        listOfSplitsForTable.add(splitToAdd);
    }

    @FXML void resetAction() {
        stopwatch.resetStopwatch();
        splitsTable.getItems().clear();
    }

    @FXML void saveToFileAction() {}



    // ...

}
