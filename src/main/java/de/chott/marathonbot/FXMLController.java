package de.chott.marathonbot;

import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.config.CredentialConfigService;
import de.chott.marathonbot.service.twitch.TwitchChatBotService;
import de.chott.marathonbot.util.config.ConfigConstants;
import static de.chott.marathonbot.util.config.ConfigConstants.TEST;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLController implements Initializable {

	@FXML
	private Label label;

	private CredentialConfigService credentialConfigService;

	@FXML
	private void handleButtonAction(ActionEvent event) {
		System.out.println("You clicked me!");

		String config = credentialConfigService.getConfig(TEST);
		if (config == null) {
                        SingletonServiceFactory.getInstance(TwitchChatBotService.class).sendMessage("Hello World!");
                        
			label.setText("no config found. Setting config");
			credentialConfigService.setConfig(TEST, "Hello World!");
			credentialConfigService.saveConfigToFile();
		} else {
			label.setText(config);
		}

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		credentialConfigService = SingletonServiceFactory.getInstance(CredentialConfigService.class);

	}
}
