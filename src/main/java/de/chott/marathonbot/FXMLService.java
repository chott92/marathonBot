/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot;

import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author CHOTT
 */
@Component
public class FXMLService {

    private ApplicationContext applicationContext;
    
    public Object load(String url) {
        try (InputStream fxmlStream = FXMLService.class
                .getResourceAsStream(url)) {
            System.err.println(FXMLService.class
                    .getResourceAsStream(url));
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> clazz) {
                    return applicationContext.getBean(clazz);
                }
            });
            return loader.load(fxmlStream);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public void startApp(ApplicationContext context, Stage stage){
        Parent root = (Parent) load("/fxml/Scene.fxml");
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }
    
}
