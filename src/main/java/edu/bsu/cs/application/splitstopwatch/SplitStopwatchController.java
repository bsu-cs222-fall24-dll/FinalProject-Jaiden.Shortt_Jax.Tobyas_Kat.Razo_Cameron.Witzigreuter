package edu.bsu.cs.application.splitstopwatch;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SplitStopwatchController {
    @FXML private Label stopwatchTimeLabel;

    @FXML private TableView<Split> splitsTable;
    @FXML private TableColumn<Split, String> splitsNameColumn;
    @FXML private TableColumn<Split, String> splitsTimeTotalColumn;
    @FXML private TableColumn<Split, String> splitsTimeSinceColumn;

    public void initialize() {
        stopwatchTimeLabel.setText("-:--.---");

        splitsTable.getItems().clear();

        splitsNameColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getSplitName()));
        splitsTimeTotalColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getTimeTotal()));
        splitsTimeSinceColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getTimeSince()));
    }



    @FXML void addNewSplitAction() {}

    @FXML void clearAction() {}

    @FXML void saveToFileAction() {}

    @FXML void startStopwatchAction() {}

    @FXML void stopStopwatchAction() {}


    // ...

}
