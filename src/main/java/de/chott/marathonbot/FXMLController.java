package de.chott.marathonbot;

import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.TestService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLController implements Initializable {

	@FXML
	private Label label;

	private TestService testService;

	@FXML
	private void handleButtonAction(ActionEvent event) {
		System.out.println("You clicked me!");
		label.setText(testService.getHelloWorld());
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		testService = SingletonServiceFactory.getInstance(TestService.class);
	}
}
