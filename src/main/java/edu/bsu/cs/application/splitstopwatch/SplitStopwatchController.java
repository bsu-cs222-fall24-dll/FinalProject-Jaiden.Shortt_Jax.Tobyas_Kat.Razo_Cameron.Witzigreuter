package edu.bsu.cs.application.splitstopwatch;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class SplitStopwatchController {
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
        splitsTimeTotalColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getTimeTotal()));
        splitsTimeSinceColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getTimeSince()));
    }



    @FXML void addNewSplitAction() {
        listOfSplitsForTable.add(new Split("Time Total", "Time Since"));
        splitsTable.setItems(FXCollections.observableList(listOfSplitsForTable));
    }

    @FXML void clearAction() {
        stopwatchTimeLabel.setText("-:--.---");
        splitsTable.getItems().clear();
    }

    @FXML void saveToFileAction() {}

    @FXML void startStopwatchAction() {}

    @FXML void stopStopwatchAction() {}


    // ...

}
