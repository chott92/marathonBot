/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot.controller;

import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import de.chott.marathonbot.model.RunConfigTableEntry;
import de.chott.marathonbot.service.SingletonService;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.data.RunConfigDataService;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class RunViewController implements Initializable {

    @FXML
    private TableColumn<RunConfigTableEntry, String> gameColumn;
    @FXML
    private TableColumn<RunConfigTableEntry, String> categoryColumn;
    @FXML
    private TableColumn<RunConfigTableEntry, String> runnerNameColumn;
    @FXML
    private TableColumn<RunConfigTableEntry, String> runnerPBColumn;
    @FXML
    private TableColumn<RunConfigTableEntry, String> wrHolderNameColumn;
    @FXML
    private TableColumn<RunConfigTableEntry, String> wrTimeColumn;
    @FXML
    private TableColumn<RunConfigTableEntry, String> speedrunComPageColumn;
    @FXML
    private TableView<RunConfigTableEntry> runTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        
        runTable.setEditable(true);
        setupColumn(gameColumn, "game", RunConfigTableEntry::setGame);
        setupColumn(categoryColumn, "category", RunConfigTableEntry::setCategory);
        setupColumn(runnerNameColumn, "runnerName", RunConfigTableEntry::setRunnerName);
        setupColumn(wrTimeColumn, "wrTime", RunConfigTableEntry::setWrTime);
        setupColumn(runnerPBColumn, "runnerPB", RunConfigTableEntry::setRunnerPB);
        setupColumn(wrHolderNameColumn, "wrHolderName", RunConfigTableEntry::setWrHolderName);
        setupColumn(speedrunComPageColumn, "speedrunComLink", RunConfigTableEntry::setSpeedrunComLink);

        runTable.setItems(SingletonServiceFactory.getInstance(RunConfigDataService.class).getData());
    }
    
    private void setupColumn (TableColumn<RunConfigTableEntry, String> column, String propertyName,
                    BiConsumer<RunConfigTableEntry, String> updateFunction){
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<RunConfigTableEntry, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<RunConfigTableEntry, String> t) {
                RunConfigTableEntry entry = (RunConfigTableEntry) t.getTableView()
                        .getItems().get(t.getTablePosition().getRow());
                updateFunction.accept(entry, t.getNewValue());
            }
        });
    }
}
