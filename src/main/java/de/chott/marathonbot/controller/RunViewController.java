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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        gameColumn.setCellValueFactory(new PropertyValueFactory<>("game"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        runnerNameColumn.setCellValueFactory(new PropertyValueFactory<>("runnerName"));
        wrTimeColumn.setCellValueFactory(new PropertyValueFactory<>("wrTime"));
        runnerPBColumn.setCellValueFactory(new PropertyValueFactory<>("runnerPB"));
        wrHolderNameColumn.setCellValueFactory(new PropertyValueFactory<>("wrHolderName"));
        speedrunComPageColumn.setCellValueFactory(new PropertyValueFactory<>("speedrunComLink"));

        runTable.setItems(SingletonServiceFactory.getInstance(RunConfigDataService.class).getData());
    }

}
