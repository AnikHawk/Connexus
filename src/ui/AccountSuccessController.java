/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
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
 * @author Aspire
 */
public class AccountSuccessController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private JFXButton ok;
    
        @FXML
    void clicked(ActionEvent event) throws Exception {
         Parent log = FXMLLoader.load(getClass().getResource("start.fxml"));
        ((Stage) ok.getScene().getWindow()).setScene(new Scene(log));

    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
