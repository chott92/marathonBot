/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot.controller;

import de.chott.marathonbot.model.RunConfigTableEntry;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.data.DashboardDataService;
import de.chott.marathonbot.service.data.RunConfigDataService;
import de.chott.marathonbot.service.util.UtilService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class DashboardController implements Initializable {

    @FXML
    private Label lastGameLabel;
    @FXML
    private Label wrCommandLabel;
    @FXML
    private Label lastTitleLabel;
    @FXML
    private ListView<RunConfigTableEntry> runOverview;
    @FXML
    private TextField nextGameInput;
    @FXML
    private TextField nextWRInput;
    @FXML
    private Button editRunListButton;
    @FXML
    private TextField nextTitleInput;

    private DashboardDataService dashboardDataService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        runOverview.setItems(SingletonServiceFactory.getInstance(RunConfigDataService.class).getData());

        dashboardDataService = SingletonServiceFactory.getInstance(DashboardDataService.class);
        updateCurrentRunFields();
        updateNextRunFields();
        runOverview.getSelectionModel().select(dashboardDataService.getNextEntry());
    }

    @FXML
    private void changeGame(ActionEvent event) {
        dashboardDataService.setCurrentEntry(dashboardDataService.getNextEntry());
        dashboardDataService.setNextEntry(null);
        dashboardDataService.setWrCommandOutput(getWrString(dashboardDataService.getCurrentEntry()));
        updateCurrentRunFields();
        updateNextRunFields();
    }

    @FXML
    private void toRunConfig(ActionEvent event) {
        try {
            SingletonServiceFactory.getInstance(UtilService.class).switchScene(editRunListButton, "/fxml/RunView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void changeRunSelection(MouseEvent event) {
        RunConfigTableEntry selectedItem = runOverview.getSelectionModel().getSelectedItem();
        
        dashboardDataService.setNextEntry(selectedItem);
        
        updateNextRunFields();
    }

    private void updateNextRunFields() {

        RunConfigTableEntry nextEntry = dashboardDataService.getNextEntry();
        if (nextEntry != null) {
            nextGameInput.setText(nextEntry.getGame());
            nextTitleInput.setText(nextEntry.getRunnerName());
            nextWRInput.setText(nextEntry.getCategory());
        } else {
            nextGameInput.setText("");
            nextTitleInput.setText("");
            nextWRInput.setText("");
            runOverview.getSelectionModel().clearSelection();
        }
    }

    private void updateCurrentRunFields() {
        RunConfigTableEntry currentEntry = dashboardDataService.getCurrentEntry();
        if (currentEntry != null) {
            lastGameLabel.setText(currentEntry.getGame());
            lastTitleLabel.setText(currentEntry.getRunnerName());
            wrCommandLabel.setText(dashboardDataService.getWrCommandOutput());
        } 
    }

    private String getWrString(RunConfigTableEntry currentEntry) {
        return "Aktueller Weltrekord für "+  currentEntry.getGame() + " (" + currentEntry.getCategory() + "): " 
                + currentEntry.getWrTime() + " von " + currentEntry.getWrHolderName();
    }

}
