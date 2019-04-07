package de.chott.marathonbot.controller;

import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.google.GoogleSheetsImportService;
import de.chott.marathonbot.service.util.UtilService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author CHOTT
 */
public class GoogleImportController implements Initializable {

	@FXML
	private TextField googleDocId;
	@FXML
	private TextField sheetName;
	@FXML
	private Button toRunViewButton;
	@FXML
	private Button startImportButton;
	@FXML
	private Label messageLabel;

	@FXML
	private void toRunView(ActionEvent event) {
		try {
			SingletonServiceFactory.getInstance(UtilService.class).switchScene(toRunViewButton, "/fxml/RunView.fxml");
		} catch (IOException ex) {
			Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	private void startImport(ActionEvent event) {

		String documentId = this.googleDocId.getText();
		String sheetName = this.sheetName.getText();

		if (SingletonServiceFactory.getInstance(GoogleSheetsImportService.class)
				.importDataFromGoogleSheet(documentId, sheetName)) {
			toRunView(event);
		} else {
			messageLabel.setText("Error during import. Please re-Check the parameters.");
		}

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}
}
