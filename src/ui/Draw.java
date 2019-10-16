/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author altam
 */
public class Draw {

    Draw(Stage primaryStage) {
        
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gp.addRow(0, cp, slider);
        gp.addRow(1,fill, save, send);
        // gp.add(save, 1, 1);
        gp.setHgap(20);
        gp.setVgap(scene.getHeight() - 80);
        gp.setAlignment(Pos.TOP_CENTER);
        Image image = new Image("file:E:\\teal.png"); //image path after "file:"
       gp.setCursor(new ImageCursor(image));

        send.setStyle("-fx-background-color:  #4169e1; -fx-text-fill: white;");
        send.setOnAction(e -> {
            takeSnapShot(scene);
        });
       
        save.setStyle("-fx-background-color:  #009688; -fx-text-fill: white;");
        save.setOnAction(e -> {
            takeSnapShot(scene);
        });

        fill.setStyle("-fx-background-color:  #009688; -fx-text-fill: white;");
        fill.setOnAction(e -> {
            gc.setFill(cp.getValue());
            gc.fillRect(0, 0, scene.getWidth(), scene.getHeight());
        });
        scene.setOnMousePressed(e -> {
            gc.beginPath();
            gc.lineTo(e.getSceneX(), e.getSceneY());
            gc.stroke();
        });
        scene.setOnMouseDragged(e -> {
            gc.lineTo(e.getSceneX(), e.getSceneY());
            gc.stroke();
        });
        cp.setValue(Color.BLACK);
        cp.setOnAction(e -> {
            gc.setStroke(cp.getValue());
        });
        slider.setMin(1);
        slider.setMax(100);
        slider.setValue(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.valueProperty().addListener(e -> {
            double value = slider.getValue();
            gc.setLineWidth(value);
        });
        root.getChildren().addAll(canvas, gp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void takeSnapShot(Scene scene) {
        WritableImage writableImage
                = new WritableImage((int) scene.getWidth(), (int) scene.getHeight()-50);
        scene.snapshot(writableImage);
        File file = new File("E:\\snapshot.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            System.out.println("snapshot saved: " + file.getAbsolutePath());
        } catch (IOException ex) {
            ;
        }
    }

    Canvas canvas = new Canvas(800, 500);
    GraphicsContext gc;
    StackPane root = new StackPane();
    Scene scene = new Scene(root, 800, 500);
    JFXColorPicker cp = new JFXColorPicker();
    JFXSlider slider = new JFXSlider();
    GridPane gp = new GridPane();
    GridPane gp2 = new GridPane();
    JFXButton save = new JFXButton("Save Snapshot");
    JFXButton fill = new JFXButton("Fill Background");
    JFXButton send = new JFXButton("Send Doodle");
    

    public static void main(String[] args) {
        new Draw(new Stage());
    }
}
