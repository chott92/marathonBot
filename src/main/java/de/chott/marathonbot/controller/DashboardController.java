/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot.controller;

import de.chott.marathonbot.model.RunConfigTableEntry;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.config.ConfigService;
import de.chott.marathonbot.service.data.DashboardDataService;
import de.chott.marathonbot.service.data.RunConfigDataService;
import de.chott.marathonbot.service.template.TemplateService;
import de.chott.marathonbot.service.twitch.TwitchChatBotService;
import de.chott.marathonbot.service.util.UtilService;
import de.chott.marathonbot.util.config.ConfigConstants;
import static de.chott.marathonbot.util.config.ConfigConstants.TITLE_TEMPLATE_NAME;
import static de.chott.marathonbot.util.config.ConfigConstants.WR_COMMAND_TEMPLATE_NAME;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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
    private CheckBox ffzFollowUpdate;
    @FXML
    private Button editRunListButton;
    
    @FXML
    private TextField nextTitleInput;
    @FXML
    private TextArea twitchTitleTemplate;
    @FXML
    private TextArea wrCommandTemplate;
    @FXML
    private Button saveTemplatesButton;
    
    
    private DashboardDataService dashboardDataService;
    private TemplateService templateService;
    private ConfigService configService;
    private TwitchChatBotService chatService;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        runOverview.setItems(SingletonServiceFactory.getInstance(RunConfigDataService.class).getData());

        dashboardDataService = SingletonServiceFactory.getInstance(DashboardDataService.class);
        templateService = SingletonServiceFactory.getInstance(TemplateService.class);
        configService = SingletonServiceFactory.getInstance(ConfigService.class);
        chatService = SingletonServiceFactory.getInstance(TwitchChatBotService.class);
        
        setupTemplateFields();
        
        updateCurrentRunFields();
        updateNextRunFields();
        runOverview.getSelectionModel().select(dashboardDataService.getNextEntry());
    }

    @FXML
    private void changeGame(ActionEvent event) {
        dashboardDataService.setCurrentEntry(dashboardDataService.getNextEntry());
        dashboardDataService.setNextEntry(null);
        
        updateCurrentRunFields();
        updateNextRunFields();
        
        chatService.sendMessage("!game " + lastGameLabel.getText());
        chatService.sendMessage("!title " 
                + lastTitleLabel.getText());
        if (ffzFollowUpdate.isSelected()){
            chatService.sendMessage("!ffzfollow " + dashboardDataService.getCurrentEntry().getRunnerName());
        }
        
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
            nextTitleInput.setText(templateService.getProcessedTemplate(TITLE_TEMPLATE_NAME, nextEntry));
            nextWRInput.setText(templateService.getProcessedTemplate(WR_COMMAND_TEMPLATE_NAME, nextEntry));
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
            lastTitleLabel.setText(templateService.getProcessedTemplate(TITLE_TEMPLATE_NAME, currentEntry));
            wrCommandLabel.setText(templateService.getProcessedTemplate(WR_COMMAND_TEMPLATE_NAME, currentEntry));
        } 
    }

    @FXML
    private void changeTemplates(ActionEvent event) {
        templateService.updateTemplate(ConfigConstants.TITLE_TEMPLATE_NAME, twitchTitleTemplate.getText());
        templateService.updateTemplate(ConfigConstants.WR_COMMAND_TEMPLATE_NAME, wrCommandTemplate.getText());
    }

    private void setupTemplateFields() {
        twitchTitleTemplate.setText(configService.getConfig(ConfigConstants.TITLE_TEMPLATE_NAME).orElse(""));
        wrCommandTemplate.setText(configService.getConfig(ConfigConstants.WR_COMMAND_TEMPLATE_NAME).orElse(""));
    }

}
