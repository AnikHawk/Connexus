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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aspire
 */
public class LoginWarningController implements Initializable {

      @FXML
    private JFXButton button;
       @FXML
    private AnchorPane bg;

    @FXML
    void clicked(ActionEvent event) throws IOException {
         Parent log = FXMLLoader.load(getClass().getResource("Login.fxml"));
        ((Stage) button.getScene().getWindow()).setScene(new Scene(log));
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       Image image = new Image("file:E:\\blue_cursor.png"); //image path after "file:"
        button.setCursor(new ImageCursor(image));
         bg.setCursor(new ImageCursor(image));
        
    }    
    
}
