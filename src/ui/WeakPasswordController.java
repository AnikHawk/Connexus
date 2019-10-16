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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author altam
 */
public class WeakPasswordController implements Initializable {

    @FXML
    private JFXButton button;

    @FXML
    void clicked(ActionEvent event) throws IOException {
         Parent signup = FXMLLoader.load(getClass().getResource("Signup.fxml"));
            ((Stage) button.getScene().getWindow()).setScene(new Scene(signup));
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
