/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
package de.chott.marathonbot.service.util;

import de.chott.marathonbot.service.SingletonService;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author chot
 */
public class UtilService implements SingletonService {

	public String getAppFolderFilepath() {
		String jarPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		String[] split = jarPath.split("/");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < split.length - 1; i++) {
			sb.append(split[i]).append("/");
		}
		return sb.toString();
	}
        
        public void switchScene(Button sourceButton, String targetScene)throws IOException{
            Stage stage = (Stage) sourceButton.getScene().getWindow();
            Parent root = FXMLLoader.load(this.getClass().getResource(targetScene));
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
        }

}
