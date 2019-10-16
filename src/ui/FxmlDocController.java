/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FxmlDocController implements Initializable {
    
    @FXML
    private AnchorPane root;
    
      @FXML
    private JFXButton login;

    @FXML
    private JFXButton signup;

    @FXML
    void loginClicked(ActionEvent event) throws IOException {
         Parent log = FXMLLoader.load(getClass().getResource("Login.fxml"));
        ((Stage) signup.getScene().getWindow()).setScene(new Scene(log));
       //  new Texting(new Stage());
    }

    @FXML
    void signupClicked(ActionEvent event) throws IOException {
         Parent sign = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        ((Stage) signup.getScene().getWindow()).setScene(new Scene(sign));
    }
    
    public void initialize(URL url, ResourceBundle rb) {
         Image image = new Image("file:E:\\blue_cursor.png"); //image path after "file:"
        root.setCursor(new ImageCursor(image));
        login.setCursor(new ImageCursor(image));
        signup.setCursor(new ImageCursor(image));
        
        
        
        
        try {
            if (!Splash.isSplashLoaded) {
               loadSplashScreen();
            }
        } catch (Exception ex) {
            Logger.getLogger(FxmlDocController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

     void loadSplashScreen() throws Exception {
        
        Pane pane;
        pane = FXMLLoader.load(getClass().getResource("screen.fxml"));
        root.getChildren().setAll(pane);

        FadeTransition fadein = new FadeTransition(Duration.seconds(2), pane);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.setCycleCount(1);

        FadeTransition fadeout = new FadeTransition(Duration.seconds(2), pane);
        fadeout.setFromValue(1);
        fadeout.setToValue(0);
        fadeout.setCycleCount(1);

        Splash.isSplashLoaded = true;

        fadein.play();
        fadein.setOnFinished((e) -> {
            fadeout.play();
        }
        );

        fadeout.setOnFinished((e) -> {
            try {
                AnchorPane parentContent = FXMLLoader.load(getClass().getResource("start.fxml"));
                root.getChildren().setAll(parentContent);
            } catch (IOException ex) {
                Logger.getLogger(FxmlDocController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        );
    }

}
