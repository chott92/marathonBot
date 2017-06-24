/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
package de.chott.marathonbot.controller;

import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.config.ConfigService;
import de.chott.marathonbot.service.twitch.TwitchChatBotService;
import de.chott.marathonbot.service.util.UtilService;
import de.chott.marathonbot.util.config.ConfigConstants;
import static de.chott.marathonbot.util.config.ConfigConstants.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
    private PasswordField twitchOauth;
    @FXML
    private TextField twitchChannel;
    @FXML
    private TitledPane x1;
    @FXML
    private Button startButton;

    ConfigService configService;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configService = SingletonServiceFactory.getInstance(ConfigService.class);
        
        twitchUsername.setText(configService.getConfig(TWITCH_USERNAME).orElse(""));
        twitchOauth.setText(configService.getConfig(TWITCH_OAUTH).orElse(""));
        twitchChannel.setText(configService.getConfig(TWITCH_CHANNEL).orElse(""));
    }

    @FXML
    private void startBots(ActionEvent event) {
       
        configService.setConfig(TWITCH_USERNAME, twitchUsername.getText());
        configService.setConfig(TWITCH_OAUTH, twitchOauth.getText());
        configService.setConfig(TWITCH_CHANNEL, twitchChannel.getText());
        
        
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
