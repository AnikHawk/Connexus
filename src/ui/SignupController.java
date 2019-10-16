/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aspire
 */
public class SignupController implements Initializable {

    @FXML
    private JFXButton button;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField rePassword;

    @FXML
    private TextField Name;
    
    @FXML
    private JFXButton back;
    
    @FXML
    void back_clicked(ActionEvent event) throws Exception {
        
        Parent log = FXMLLoader.load(getClass().getResource("start.fxml"));
        ((Stage) back.getScene().getWindow()).setScene(new Scene(log));

    }

    @FXML
    void clicked(ActionEvent event) throws IOException {
        String name = Name.getText();
        String pass = password.getText();
        String rePass = rePassword.getText();
        
        
         
      
        Scanner scan = new Scanner(pass);
        char ch;

        int len = pass.length();
        int digit = 0;
        int lowerCase = 0;
        int upperCase = 0;
        int count = 0;

        if (len >= 6) {
            while (scan.hasNext()) {
                ch = pass.charAt(count);
                if (Character.isDigit(ch)) {
                    digit = digit + 1;
                }
                if (Character.isLowerCase(ch)) {
                    lowerCase = lowerCase + 1;
                }
                if (Character.isUpperCase(ch)) {
                    upperCase = upperCase + 1;
                }
                if (count < pass.length() - 1) {
                    count = count + 1;
                } else {
                    break;
                }
            }
        }
/*
        System.out.println(digit);
        System.out.println(lowerCase);
        System.out.println(upperCase);
*/
        if (digit < 1 || lowerCase < 1 || upperCase < 1 || len<6) {
         Parent weak = FXMLLoader.load(getClass().getResource("WeakPassword.fxml"));
            ((Stage) button.getScene().getWindow()).setScene(new Scene(weak));
        } 


        else if (!pass.equals(rePass)) {
            Parent noMatch = FXMLLoader.load(getClass().getResource("NotMatched.fxml"));
            ((Stage) button.getScene().getWindow()).setScene(new Scene(noMatch));
        }
        
       
        else{
        File f = new File("E:\\record.txt");
        FileWriter fileWriter = new FileWriter(f, true);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.newLine();
        bw.write(name + " " + pass);
        bw.newLine();
        bw.close();
        
          Parent start = FXMLLoader.load(getClass().getResource("AccountSuccess.fxml"));
            ((Stage) button.getScene().getWindow()).setScene(new Scene(start));
        
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
