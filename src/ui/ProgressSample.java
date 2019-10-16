/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Aspire
 */
public class ProgressSample {
    
    
 
 
    final Float value = -1.0f;
    final Label labels = new Label();
    final ProgressBar pb = new ProgressBar();
    final ProgressIndicator pin = new ProgressIndicator();
    final HBox hb = new HBox();
 
          ProgressSample(Stage stage)
    {
        Group root = new Group();
        Scene scene = new Scene(root, 350 , 200);
        stage.setScene(scene);
        stage.setTitle("Progress Controls");
      
 
        
            pb.setProgress(value);
            System.out.println("PRINTING");
 
           
            pin.setProgress(value);
            pin.setMaxSize(70, 70);

            hb.setSpacing(5);
           
            hb.setAlignment(Pos.TOP_CENTER);
    
            hb.getChildren().addAll( pin);
        

       BorderPane container = new BorderPane(pin, null, null, null, null);
        

        container.getChildren().addAll(hb);
        scene.setRoot(container);
        stage.show();
    }
    }   

