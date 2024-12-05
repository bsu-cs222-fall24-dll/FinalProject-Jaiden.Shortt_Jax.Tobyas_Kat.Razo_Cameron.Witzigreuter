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
    Stopwatch stopwatch = new Stopwatch();
    ObservableList<Split> listOfSplitsForTable;

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



    @FXML void addNewSplitAction() {
        listOfSplitsForTable.add(new Split(String.valueOf(stopwatch.getTimeElapsedFormatted()), "Time Since"));
        splitsTable.setItems(FXCollections.observableList(listOfSplitsForTable));
    }

    @FXML void resetAction() {
        stopwatch.resetStopwatch();
        splitsTable.getItems().clear();
    }

    @FXML void saveToFileAction() {}

    @FXML void startStopwatchAction() {
        stopwatch.startStopwatch(stopwatchTimeLabel);
    }

    @FXML void stopStopwatchAction() {
        stopwatch.stopStopwatch();
    }


    // ...

}
