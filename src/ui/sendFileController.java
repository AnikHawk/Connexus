/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Aspire
 */


public class sendFileController implements Initializable{

    @FXML
    private JFXButton shareButton;

    @FXML
    void chooseFile(ActionEvent event) throws Exception {
        String s="";
        FileChooser fc = new FileChooser();
        File SelectedFile = fc.showOpenDialog(null);
        if(SelectedFile!= null)
        {
            s =  SelectedFile.getAbsolutePath();
        }
        else System.out.println("Error file");
        
        ServerSocket serverSocket = new ServerSocket(6666);
        Socket socket = serverSocket.accept();
        System.out.println("Accepted connection : " + socket);
        
        File transferFile = new File(s);
        FileInputStream fin = new FileInputStream(transferFile);
        BufferedInputStream bin = new BufferedInputStream(fin);
        
         byte[] bytearray = new byte[(int) transferFile.length()];
         System.out.println(bytearray.length);
        bin.read(bytearray, 0, bytearray.length);
        OutputStream os = socket.getOutputStream();
        System.out.println("Sending Files...");
        os.write(bytearray, 0, bytearray.length);
        os.flush();
        socket.close();
        System.out.println("File transfer complete");
    }

    
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
