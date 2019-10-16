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
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author altam
 */
public class homeController implements Initializable {

    @FXML
    private JFXButton texting;
    @FXML
    private JFXButton ttt;
    @FXML
    private JFXButton painting;
    @FXML
    private JFXButton share;
    @FXML
    private AnchorPane root;
       
    @FXML
    private JFXButton logout;


    /**
     * Initializes the controller class.
     */
        @FXML
    void Action_logout(ActionEvent event) throws Exception {
        
        Parent log = FXMLLoader.load(getClass().getResource("start.fxml"));
        ((Stage) logout.getScene().getWindow()).setScene(new Scene(log));

    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      Image image = new Image("file:E:\\blue_cursor.png"); //image path after "file:"
        root.setCursor(new ImageCursor(image));
    }    

    @FXML
    private void Action_Texting(MouseEvent event) {
        new Texting(new Stage());
       //new Texting( ((Stage) ttt.getScene().getWindow()));
    }

    @FXML
    private void Action_ttt(MouseEvent event) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("Home.fxml"));
        ((Stage) ttt.getScene().getWindow()).setScene(new Scene(home));
        new TTT();
         
    }

    @FXML
    private void Action_painting(MouseEvent event) throws Exception{
        new Draw(new Stage());
    }

    @FXML
    private void Action_share(MouseEvent event) throws IOException {
         Parent send = FXMLLoader.load(getClass().getResource("sendFile.fxml"));
     ((Stage) share.getScene().getWindow()).setScene(new Scene(send));   
    }
    
}
