/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot.controller;

import de.chott.marathonbot.model.RunConfigTableEntry;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.data.RunConfigDataService;
import de.chott.marathonbot.service.util.UtilService;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    @FXML
    private TextField gameInput;
    @FXML
    private TextField categoryInput;
    @FXML
    private TextField runnerNameInput;
    @FXML
    private TextField runnerPBInput;
    @FXML
    private TextField wrHolderNameInput;
    @FXML
    private TextField speedrunComInput;
    @FXML
    private TextField wrTimeInput;

    ObservableList<RunConfigTableEntry> data;
    @FXML
    private Button toDasboardButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        
        data = SingletonServiceFactory.getInstance(RunConfigDataService.class).getData();
        
        runTable.setEditable(true);
        setupColumn(gameColumn, "game", RunConfigTableEntry::setGame);
        setupColumn(categoryColumn, "category", RunConfigTableEntry::setCategory);
        setupColumn(runnerNameColumn, "runnerName", RunConfigTableEntry::setRunnerName);
        setupColumn(wrTimeColumn, "wrTime", RunConfigTableEntry::setWrTime);
        setupColumn(runnerPBColumn, "runnerPB", RunConfigTableEntry::setRunnerPB);
        setupColumn(wrHolderNameColumn, "wrHolderName", RunConfigTableEntry::setWrHolderName);
        setupColumn(speedrunComPageColumn, "speedrunComLink", RunConfigTableEntry::setSpeedrunComLink);

        runTable.setItems(data);
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

   

    @FXML
    private void addRun(ActionEvent event) {
        
        if (gameInput.getText().isEmpty() || runnerNameInput.getText().isEmpty()){
            return;
        }
        RunConfigTableEntry entry = new RunConfigTableEntry(gameInput.getText(), runnerNameInput.getText(), 
                wrTimeInput.getText(), runnerPBInput.getText(), wrHolderNameInput.getText(), categoryInput.getText(),
                speedrunComInput.getText());
        
        data.add(entry);
        
        clearTextFields();
    }
    
     @FXML
    private void toDashboard(ActionEvent event) {
        try {
            SingletonServiceFactory.getInstance(UtilService.class).switchScene(toDasboardButton, "/fxml/Dashboard.fxml");
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearTextFields() {
        Arrays.asList(gameInput, runnerPBInput, runnerNameInput, wrTimeInput, wrHolderNameInput,
                categoryInput, speedrunComInput).forEach(field -> field.setText(""));
    }

    @FXML
    private void deleteRun(ActionEvent event) {
        RunConfigTableEntry selectedItem = runTable.getSelectionModel().getSelectedItem();
        data.remove(selectedItem);
        runTable.getSelectionModel().clearSelection();
    }
}
