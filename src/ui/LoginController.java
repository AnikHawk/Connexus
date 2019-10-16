/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aspire
 */
public class LoginController implements Initializable {

     @FXML
    private JFXButton button;

    @FXML
    private JFXTextField Name;

    @FXML
    private JFXPasswordField Password;
    
    @FXML
    private JFXButton back;
    
   @FXML
    private AnchorPane bg;
   
    @FXML
    void back_clicked(ActionEvent event) throws Exception {
        
    Parent log = FXMLLoader.load(getClass().getResource("start.fxml"));
        ((Stage) back.getScene().getWindow()).setScene(new Scene(log));
        
        

    }

    @FXML
    void clicked(ActionEvent event) throws FileNotFoundException, IOException {
         File f = new File("E:\\record.txt");
         /*
         ClassLoader classLoader = getClass().getClassLoader();
         File f = new File(classLoader.getResource("/record.txt").getFile());
         */

        FileReader fileReader = new FileReader(f);
        BufferedReader br = new BufferedReader(fileReader);

        Scanner sc = new Scanner(br);
        String name;
        String pass;

        HashMap<String, String> map = new HashMap<>();

        while (sc.hasNext()) {
            name = sc.next();
            pass = sc.next();
            map.put(name, pass);
        }
        br.close();

       
        name = Name.getText();
        pass = Password.getText();

        if (map.containsKey(name)) {
            if (map.get(name).equals(pass)) {
               Parent home = FXMLLoader.load(getClass().getResource("Home.fxml"));
        ((Stage) button.getScene().getWindow()).setScene(new Scene(home));
                
            } else {
                 Parent log = FXMLLoader.load(getClass().getResource("loginWarning.fxml"));
        ((Stage) button.getScene().getWindow()).setScene(new Scene(log));
            }
        } else {
             Parent log = FXMLLoader.load(getClass().getResource("loginWarning.fxml"));
        ((Stage) button.getScene().getWindow()).setScene(new Scene(log));
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         Image image = new Image("file:E:\\blue_cursor.png"); //image path after "file:"
        button.setCursor(new ImageCursor(image));
        bg.setCursor(new ImageCursor(image));
        
       
    }    
    
}
