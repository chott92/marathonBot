package de.chott.marathonbot;

import de.chott.marathonbot.service.SingletonServiceFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/CredentialsConfig.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add("/styles/Styles.css");

		stage.setTitle("Mr.Tiger's Marathon Bot");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the
	 * application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans
	 * ignores main().
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop() {
		SingletonServiceFactory.closeServices();
		System.exit(0);
	}

}
