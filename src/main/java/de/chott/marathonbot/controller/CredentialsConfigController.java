/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
package de.chott.marathonbot.controller;

import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.twitch.TwitchChatBotService;
import de.chott.marathonbot.service.util.UtilService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author chot
 */
public class CredentialsConfigController implements Initializable {

    @FXML
    private TextField twitchUsername;
    @FXML
    private TextField twitchOauth;
    @FXML
    private TextField twitchChannel;
    @FXML
    private TitledPane x1;
    @FXML
    private Button startButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void startBots(ActionEvent event) {
       
        
        try {
            SingletonServiceFactory.getInstance(TwitchChatBotService.class)
                    .startBot(twitchUsername.getText(),
                            twitchOauth.getText(),
                            twitchChannel.getText());
            
            SingletonServiceFactory.getInstance(UtilService.class).switchScene(startButton, "/fxml/Dashboard.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
