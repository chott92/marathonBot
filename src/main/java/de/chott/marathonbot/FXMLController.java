package de.chott.marathonbot;

import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.config.CredentialConfigService;
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

		String config = credentialConfigService.getConfig("test");
		if (config == null) {
			label.setText("no config found. Setting config");
			credentialConfigService.setConfig("test", "Hello World!");
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
