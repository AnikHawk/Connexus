/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UI extends Application {

    public static boolean isSplashLoaded = false;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Image image = new Image("file:E:\\blue_cursor.png"); 
        scene.setCursor(new ImageCursor(image));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);

       
    }

  
 
}
    


