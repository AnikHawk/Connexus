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
 * @author altam
 */
public class NotMatchedController implements Initializable {

    @FXML
    private JFXButton ok;
   
       @FXML
    private AnchorPane bg;

    
     @FXML
    void clicked(ActionEvent event) throws IOException {
         Parent signup = FXMLLoader.load(getClass().getResource("Signup.fxml"));
            ((Stage) ok.getScene().getWindow()).setScene(new Scene(signup));
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       Image image = new Image("file:E:\\blue_cursor.png"); //image path after "file:"
        ok.setCursor(new ImageCursor(image));
         bg.setCursor(new ImageCursor(image));
        
    }    
    
}
