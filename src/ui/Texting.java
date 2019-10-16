package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Adjustable;
import static java.awt.Color.WHITE;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.Transition;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class Texting extends Thread {

    TextField text = new TextField();
    ServerSocket ss = null;
    Socket s = null;
    DataInputStream din = null;
    DataOutputStream dout = null;
    ScrollPane scroller = new ScrollPane();
    VBox content = new VBox(10);
    VBox content2 = new VBox(10);
    StackPane anchorPane = new StackPane();
    BorderPane container;
    BorderPane border;
    HBox bottom;
    boolean isImageServer = false;

    private boolean connect() {
        try {
            s = new Socket("localhost", 2222);
            dout = new DataOutputStream(s.getOutputStream());
            din = new DataInputStream(s.getInputStream());

        } catch (IOException e) {
            //System.out.println("Unable to connect to the address: " + ip + ":" + port + " | Starting a server");
            return false;
        }
        System.out.println("Successfully connected to the server.");
        return true;
    }

    private void initializeServer() {
        try {
            ss = new ServerSocket(2222);
            // Stage stage1= new Stage();
            //new ProgressSample(stage1);
            s = ss.accept();
            //stage1.close();
            dout = new DataOutputStream(s.getOutputStream());
            din = new DataInputStream(s.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Texting(Stage primaryStage) {
        primaryStage.setOnCloseRequest(e -> {
            try {
                s.close();
            } catch (IOException ex) {
                Logger.getLogger(Texting.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ss.close();
            } catch (NullPointerException ex) {
                Logger.getLogger(Texting.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Texting.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        if (!connect()) {
            initializeServer();
        }
        new rcv().start();
         new ImageClient();
        new FileClient();
       

        primaryStage.setHeight(700);
        primaryStage.setWidth(1100);
        // primaryStage.setOpacity(.8);
        //primaryStage.setOpacity(.5);
        final Random rng = new Random();

        text = new TextField();

        text.setStyle(" -fx-min-height: 50;");
        content = new VBox(10);
        content2 = new VBox(10);

        container = new BorderPane(null, null, content2, null, content);
        container.setPrefHeight(primaryStage.getHeight() - 95);
        //container.setStyle("-fx-background-color:  #FFFFFF;");
        Image image = new Image("file:E:\\sat.png", primaryStage.getWidth(), primaryStage.getHeight() - 75, false, true);
        ImageView i = new ImageView(image);
        BackgroundImage bgImg = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(500, 600, false, false, true, true));
        container.setBackground(new Background(bgImg));
        content.setStyle("-fx-background-color:  transparent;");
        content2.setStyle("-fx-background-color:  transparent;");
        // container.setAlignment(content,Pos.CENTER_LEFT);
        // container.setAlignment(content2,Pos.CENTER_RIGHT);
        scroller = new ScrollPane(container);
        scroller.setStyle("-fx-background-color:  transparent");
        //scroller.setStyle("-fx-background-color:  transparent;");
        scroller.setFitToWidth(true);

        JFXButton addButton = new JFXButton("Send");
        addButton.setButtonType(JFXButton.ButtonType.RAISED);
        DropShadow shadow = new DropShadow();
        addButton.setEffect(shadow);
        addButton.setStyle("-fx-background-color:  #009688; -fx-text-fill: white;");

        //paint
        Image imgPaint = new Image(getClass().getResourceAsStream("/paint.png"));
        ImageView imageView = new ImageView(imgPaint);
        imageView.setFitWidth(40);
        imageView.setFitHeight(37);

        JFXButton paint = new JFXButton();
        paint.setButtonType(JFXButton.ButtonType.RAISED);
        // paint.setEffect(shadow);
        paint.setStyle("-fx-background-color:  transparent;");
        paint.setGraphic(imageView);

        paint.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");
        // paint.setGraphic(new ImageView(imgPaint));
        paint.setOnAction(e -> {
             isImageServer = true;
            Draw d =  new Draw(new Stage());
            d.send.setOnAction(a->{
             d.takeSnapShot(d.scene);
            File SelectedFile = null;
            String s = "";
            try {
                SelectedFile = new File("E:\\snapshot.png");
                if (SelectedFile != null) {
                    s = SelectedFile.getAbsolutePath();
                } else {
                    System.out.println("Error file");
                }
            } catch (Exception ex) {
                Logger.getLogger(Texting.class.getName()).log(Level.SEVERE, null, ex);
            }
             ImageServer imgServer=null;

            try {
                
                 imgServer =new ImageServer(SelectedFile.getAbsolutePath());
            } catch (Exception ex) {
                Logger.getLogger(Texting.class.getName()).log(Level.SEVERE, null, ex);
            }

            anchorPane = new StackPane();
            anchorPane.setStyle("-fx-background-color:  transparent;");

            // new send(message2).start();
            JFXButton newButton = new JFXButton();
            Image img = new Image(SelectedFile.toURI().toString());
            ImageView SentImage = new ImageView(img);
            SentImage.setFitWidth(300);
            SentImage.setPreserveRatio(true);
            //SentImage.setFitHeight(42);
            // JFXButton newButton = new JFXButton();
            // newButton.setButtonType(JFXButton.ButtonType.RAISED);
            // paint.setEffect(shadow);
            //ImageShare.setStyle("-fx-background-color:  transparent;");
            newButton.setGraphic(SentImage);
            newButton.setButtonType(JFXButton.ButtonType.RAISED);
            newButton.setStyle("-fx-background-color:  transparent; -fx-text-fill: white; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
            newButton.setOpacity(1);
            AnchorPane.setLeftAnchor(newButton, 5.0);
            AnchorPane.setTopAnchor(newButton, 5.0);
            anchorPane.getChildren().addAll(newButton);
            anchorPane.setAlignment(newButton, Pos.CENTER_RIGHT);
            content2.getChildren().add(anchorPane);
            scroller.vvalueProperty().bind(content2.heightProperty());

            //Other Side
            anchorPane = new StackPane();
            anchorPane.setStyle("-fx-background-color:  transparent;");
            JFXButton leftButton = new JFXButton();
            leftButton.setPrefHeight(img.getHeight()/img.getWidth()*300);
            leftButton.setButtonType(JFXButton.ButtonType.FLAT);
            leftButton.setStyle("-fx-background-color:  transparent; -fx-text-fill: transparent;");
            //leftButton.setOpacity(1);
            AnchorPane.setLeftAnchor(leftButton, 5.0);
            AnchorPane.setTopAnchor(leftButton, 5.0);
            anchorPane.getChildren().addAll(leftButton);
            anchorPane.setAlignment(leftButton, Pos.CENTER_LEFT);
            content.getChildren().add(anchorPane);
            scroller.vvalueProperty().bind(content.heightProperty());
            isImageServer=false;
            
            try {
                imgServer.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Texting.class.getName()).log(Level.SEVERE, null, ex);
            }
            new ImageClient();
       
             
            });
           
             
        });

        //TTT
        Image imgTTT = new Image(getClass().getResourceAsStream("ttt.png"));
        ImageView imageView1 = new ImageView(imgTTT);
        imageView1.setFitWidth(40);
        imageView1.setFitHeight(37);

        JFXButton TTT = new JFXButton();
        TTT.setButtonType(JFXButton.ButtonType.RAISED);
        // paint.setEffect(shadow);
        TTT.setStyle("-fx-background-color:  transparent;");
        TTT.setGraphic(imageView1);

        TTT.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");
        TTT.setOnAction(e -> {
            new TTT();
        });

        Image imgFile = new Image(getClass().getResourceAsStream("fileShare.png"));
        ImageView imageView2 = new ImageView(imgFile);
        imageView2.setFitWidth(40);
        imageView2.setFitHeight(42);

        JFXButton file = new JFXButton();
        file.setButtonType(JFXButton.ButtonType.RAISED);
        // paint.setEffect(shadow);
        file.setStyle("-fx-background-color:  transparent;");
        file.setGraphic(imageView2);

        file.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");
        file.setOnAction(e -> {
            File SelectedFile = null;
            try {
                String s = "";
                FileChooser fc = new FileChooser();
                SelectedFile = fc.showOpenDialog(null);
                if (SelectedFile != null) {
                    s = SelectedFile.getAbsolutePath();
                } else {
                    System.out.println("Error file");
                }
            } catch (Exception ex) {
                Logger.getLogger(Texting.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                new FileServer(SelectedFile.getAbsolutePath());
            } catch (Exception ex) {
                Logger.getLogger(Texting.class.getName()).log(Level.SEVERE, null, ex);
            }

            anchorPane = new StackPane();
            anchorPane.setStyle("-fx-background-color:  transparent;");

            String message = "Sent file: " + SelectedFile.getName();
            String message2 = "Received file: " + SelectedFile.getName();
            if (!message.equals("")) {
                new send(message2).start();
                JFXButton newButton = new JFXButton("");
                Animation animation = new Transition() {
                    {
                        setCycleDuration(Duration.millis(500));
                    }

                    protected void interpolate(double frac) {
                        int length = message.length();
                        int n = Math.round(length * (float) frac);
                        newButton.setText(message.substring(0, n));
                    }

                };
                animation.play();
                newButton.setButtonType(JFXButton.ButtonType.RAISED);
                newButton.setStyle("-fx-background-color:  #808080; -fx-text-fill: white; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
                newButton.setOpacity(1);
                AnchorPane.setLeftAnchor(newButton, 5.0);
                AnchorPane.setTopAnchor(newButton, 5.0);
                anchorPane.getChildren().addAll(newButton);
                anchorPane.setAlignment(newButton, Pos.CENTER_RIGHT);
                content2.getChildren().add(anchorPane);
                scroller.vvalueProperty().bind(content2.heightProperty());

                //Other Side
                anchorPane = new StackPane();
                anchorPane.setStyle("-fx-background-color:  transparent;");
                JFXButton leftButton = new JFXButton("");
                leftButton.setButtonType(JFXButton.ButtonType.FLAT);
                leftButton.setStyle("-fx-background-color:  transparent; -fx-text-fill: transparent;");
                //leftButton.setOpacity(1);
                AnchorPane.setLeftAnchor(leftButton, 5.0);
                AnchorPane.setTopAnchor(leftButton, 5.0);
                anchorPane.getChildren().addAll(leftButton);
                anchorPane.setAlignment(leftButton, Pos.CENTER_LEFT);
                content.getChildren().add(anchorPane);
                scroller.vvalueProperty().bind(content.heightProperty());

            }

        });

        Image imgShare = new Image(getClass().getResourceAsStream("image.png"));
        ImageView imageView3 = new ImageView(imgShare);
        imageView3.setFitWidth(40);
        imageView3.setFitHeight(42);

        JFXButton ImageShare = new JFXButton();
        ImageShare.setButtonType(JFXButton.ButtonType.RAISED);
        // paint.setEffect(shadow);
        ImageShare.setStyle("-fx-background-color:  transparent;");
        ImageShare.setGraphic(imageView3);
        ImageShare.setOnAction(e -> {
            isImageServer = true;
            File SelectedFile = null;
            String s = "";
            try {
                
                FileChooser fc = new FileChooser();
                SelectedFile = fc.showOpenDialog(null);
                if (SelectedFile != null) {
                    s = SelectedFile.getAbsolutePath();
                } else {
                    System.out.println("Error file");
                }
            } catch (Exception ex) {
                Logger.getLogger(Texting.class.getName()).log(Level.SEVERE, null, ex);
            }

            ImageServer imgServer = null;
            try {
                
              imgServer = new ImageServer(SelectedFile.getAbsolutePath());
            } catch (Exception ex) {
                Logger.getLogger(Texting.class.getName()).log(Level.SEVERE, null, ex);
            }

            anchorPane = new StackPane();
            anchorPane.setStyle("-fx-background-color:  transparent;");

            // new send(message2).start();
            JFXButton newButton = new JFXButton();
            Image img = new Image(SelectedFile.toURI().toString());
            ImageView SentImage = new ImageView(img);
            SentImage.setFitWidth(300);
            SentImage.setPreserveRatio(true);
            //SentImage.setFitHeight(42);
            // JFXButton newButton = new JFXButton();
            // newButton.setButtonType(JFXButton.ButtonType.RAISED);
            // paint.setEffect(shadow);
            //ImageShare.setStyle("-fx-background-color:  transparent;");
            newButton.setGraphic(SentImage);
            newButton.setPrefHeight(SentImage.getY());
            newButton.setButtonType(JFXButton.ButtonType.RAISED);
            newButton.setStyle("-fx-background-color:  transparent; -fx-text-fill: white; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
            newButton.setOpacity(1);
            AnchorPane.setLeftAnchor(newButton, 5.0);
            AnchorPane.setTopAnchor(newButton, 5.0);
            anchorPane.getChildren().addAll(newButton);
            anchorPane.setAlignment(newButton, Pos.CENTER_RIGHT);
            content2.getChildren().add(anchorPane);
            scroller.vvalueProperty().bind(content2.heightProperty());

            //Other Side
            anchorPane = new StackPane();
            anchorPane.setStyle("-fx-background-color:  transparent;");
            JFXButton leftButton = new JFXButton("");
            leftButton.setPrefHeight(img.getHeight()/img.getWidth()*300);
            leftButton.setButtonType(JFXButton.ButtonType.FLAT);
            leftButton.setStyle("-fx-background-color:  transparent; -fx-text-fill: transparent;");
            //leftButton.setOpacity(1);
            AnchorPane.setLeftAnchor(leftButton, 5.0);
            AnchorPane.setTopAnchor(leftButton, 5.0);
            anchorPane.getChildren().addAll(leftButton);
            anchorPane.setAlignment(leftButton, Pos.CENTER_LEFT);
            content.getChildren().add(anchorPane);
            scroller.vvalueProperty().bind(content.heightProperty());
            isImageServer=false;
            
            try {
                imgServer.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Texting.class.getName()).log(Level.SEVERE, null, ex);
            }
            new ImageClient();
        });

        ImageShare.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");

        text.setOnAction(e -> {

            anchorPane = new StackPane();
            anchorPane.setStyle("-fx-background-color:  transparent;");

            String message = text.getText();
            text.clear();

            if (!message.equals("")) {
                new send(message).start();
                JFXButton newButton = new JFXButton("");
                Animation animation = new Transition() {
                    {
                        setCycleDuration(Duration.millis(500));
                    }

                    protected void interpolate(double frac) {
                        int length = message.length();
                        int n = Math.round(length * (float) frac);
                        newButton.setText(message.substring(0, n));
                    }

                };
                animation.play();
                newButton.setButtonType(JFXButton.ButtonType.RAISED);
                newButton.setStyle("-fx-background-color:  #009688; -fx-text-fill: white; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
                newButton.setOpacity(1);
                AnchorPane.setLeftAnchor(newButton, 5.0);
                AnchorPane.setTopAnchor(newButton, 5.0);
                anchorPane.getChildren().addAll(newButton);
                anchorPane.setAlignment(newButton, Pos.CENTER_RIGHT);
                content2.getChildren().add(anchorPane);
                scroller.vvalueProperty().bind(content2.heightProperty());

                //Other Side
                anchorPane = new StackPane();
                anchorPane.setStyle("-fx-background-color:  transparent;");
                JFXButton leftButton = new JFXButton("");
                leftButton.setButtonType(JFXButton.ButtonType.FLAT);
                leftButton.setStyle("-fx-background-color:  transparent; -fx-text-fill: transparent;");
                //leftButton.setOpacity(1);
                AnchorPane.setLeftAnchor(leftButton, 5.0);
                AnchorPane.setTopAnchor(leftButton, 5.0);
                anchorPane.getChildren().addAll(leftButton);
                anchorPane.setAlignment(leftButton, Pos.CENTER_LEFT);
                content.getChildren().add(anchorPane);
                scroller.vvalueProperty().bind(content.heightProperty());

            }

        });

        addButton.setOnAction(e -> {
            anchorPane = new StackPane();
            anchorPane.setStyle("-fx-background-color:  transparent;");

            String message = text.getText();
            text.clear();

            if (!message.equals("")) {
                new send(message).start();
                JFXButton newButton = new JFXButton("");
                Animation animation = new Transition() {
                    {
                        setCycleDuration(Duration.millis(500));
                    }

                    protected void interpolate(double frac) {
                        int length = message.length();
                        int n = Math.round(length * (float) frac);
                        newButton.setText(message.substring(0, n));
                    }

                };
                animation.play();
                newButton.setButtonType(JFXButton.ButtonType.RAISED);
                newButton.setStyle("-fx-background-color:  #009688; -fx-text-fill: white; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
                newButton.setOpacity(1);
                AnchorPane.setLeftAnchor(newButton, 5.0);
                AnchorPane.setTopAnchor(newButton, 5.0);
                anchorPane.getChildren().addAll(newButton);
                anchorPane.setAlignment(newButton, Pos.CENTER_RIGHT);
                content2.getChildren().add(anchorPane);
                scroller.vvalueProperty().bind(content2.heightProperty());

                //Other Side
                anchorPane = new StackPane();
                anchorPane.setStyle("-fx-background-color:  transparent;");
                JFXButton leftButton = new JFXButton("");
                leftButton.setButtonType(JFXButton.ButtonType.FLAT);
                leftButton.setStyle("-fx-background-color:  transparent; -fx-text-fill: transparent;");
                //leftButton.setOpacity(1);
                AnchorPane.setLeftAnchor(leftButton, 5.0);
                AnchorPane.setTopAnchor(leftButton, 5.0);
                anchorPane.getChildren().addAll(leftButton);
                anchorPane.setAlignment(leftButton, Pos.CENTER_LEFT);
                content.getChildren().add(anchorPane);
                scroller.vvalueProperty().bind(content.heightProperty());

            }

        });

        bottom = new HBox(5.0, file, ImageShare, paint, TTT, text, addButton);
        bottom.setStyle("-fx-background-color:  transparent;");
        bottom.setAlignment(Pos.TOP_CENTER);
        text.setAlignment(Pos.CENTER);
        text.prefWidthProperty().bind(primaryStage.widthProperty().subtract(primaryStage.getWidth() / 3.19));
        addButton.prefHeightProperty().bind(bottom.heightProperty());

        border = new BorderPane(scroller, null, null, bottom, null);
        border.setStyle("-fx-background-color:  transparent;");
        Scene scene = new Scene((border), primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    class send extends Thread {

        String s1;

        send(String str) {
            s1 = str;
        }

        public void run() {
            try {
                dout.writeUTF(s1);
                System.out.println("sending:" + s1);
            } catch (IOException ex) {
                //Logger.getLogger(jp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class rcv extends Thread {

        String str;

        public void run() {
            while (true) {
                // new FileClient();
                String str = null;
                try {
                    str = din.readUTF();
                    final String message = new String(str);
                    Platform.runLater(() -> {
                        rcv_process(message);
                    });
                } catch (IOException ex) {
                }

            }

        }
    }

    public void rcv_process(String str) {
        anchorPane = new StackPane();
        anchorPane.setStyle("-fx-background-color:  transparent;"); //rect bg
        String message = str;
        System.out.println("receiving:" + message);
        text.clear();
        if (!message.equals("") || message != null) {
            JFXButton newButton = new JFXButton("");
            Animation animation = new Transition() {
                {
                    setCycleDuration(Duration.millis(500));
                }

                protected void interpolate(double frac) {
                    int length = message.length();
                    int n = Math.round(length * (float) frac);
                    newButton.setText(message.substring(0, n));
                }

            };
            animation.play();

            newButton.setButtonType(JFXButton.ButtonType.RAISED);
            if (message.startsWith("Received file: ")) {
                newButton.setStyle("-fx-background-color:  #808080; -fx-text-fill: white; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
            } else {
                newButton.setStyle("-fx-background-color:  #4169e1; -fx-text-fill: white; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
            }
            AnchorPane.setLeftAnchor(newButton, 5.0);
            AnchorPane.setTopAnchor(newButton, 5.0);
            anchorPane.getChildren().addAll(newButton);
            anchorPane.setAlignment(newButton, Pos.CENTER_LEFT);
            content.getChildren().add(anchorPane);
            scroller.vvalueProperty().bind(content.heightProperty());

            //Other Side
            anchorPane = new StackPane();
            anchorPane.setStyle("-fx-background-color:  transparent;");
            text.clear();
            JFXButton rightButton = new JFXButton("");
            rightButton.setButtonType(JFXButton.ButtonType.FLAT);
            rightButton.setStyle("-fx-background-color:  transparent; -fx-text-fill: transparent;");
            AnchorPane.setLeftAnchor(rightButton, 5.0);
            AnchorPane.setTopAnchor(rightButton, 5.0);
            anchorPane.getChildren().addAll(rightButton);
            anchorPane.setAlignment(rightButton, Pos.CENTER_RIGHT);
            content2.getChildren().add(anchorPane);
            scroller.vvalueProperty().bind(content2.heightProperty());
        }
    }

    
   
    
     class ImageClient extends Thread{

    ImageClient()
    {
        start();
      
    }
    @Override
   public void run()  {
       while(!isImageServer){
        try {
            System.out.println("Running Loop");
            int filesize = 500000000;
            int bytesRead;
            int currentTot = 0;
            
            Socket socket = new Socket("localhost", 9999);
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = "";
            message = br.readLine();
            System.out.println("Message received from the server : " +message);
            socket.close();
            
           
            socket = new Socket("localhost", 9998);
            byte[] bytearray = new byte[filesize];
            is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Aspire\\Desktop\\Copy."+message);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bytesRead = is.read(bytearray, 0, bytearray.length);
            System.out.println("Bytes Read: "+bytesRead);
            currentTot = bytesRead;
            
            
            do {
                bytesRead
                        = is.read(bytearray, currentTot, (bytearray.length - currentTot));
                if (bytesRead > 0) {
                    currentTot += bytesRead;
                }
            } while (bytesRead > 0);
            
            
            
            bos.write(bytearray, 0, currentTot);
            bos.flush();
            bos.close();
            socket.close();
                System.out.println("File received: ");
                final File f =new File("C:\\Users\\Aspire\\Desktop\\Copy."+message);
            Platform.runLater(() -> {
                System.out.println("Run  Later");
                        img_rcv_process(f);
                    });
        } catch (Exception ex) {
            Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    }
}

     public void img_rcv_process(File SelectedFile)
    {
            anchorPane = new StackPane();
            anchorPane.setStyle("-fx-background-color:  transparent;");
        JFXButton newButton = new JFXButton();
            Image img = new Image(SelectedFile.toURI().toString());
            ImageView SentImage = new ImageView(img);
            SentImage.setFitWidth(300);
            SentImage.setPreserveRatio(true);
            //SentImage.setFitHeight(42);
            // JFXButton newButton = new JFXButton();
            // newButton.setButtonType(JFXButton.ButtonType.RAISED);
            // paint.setEffect(shadow);
            //ImageShare.setStyle("-fx-background-color:  transparent;");
            newButton.setGraphic(SentImage);
            newButton.setPrefHeight(SentImage.getY());
            newButton.setButtonType(JFXButton.ButtonType.RAISED);
            newButton.setStyle("-fx-background-color:  transparent; -fx-text-fill: white; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
            newButton.setOpacity(1);
            anchorPane.getChildren().addAll(newButton);
            anchorPane.setAlignment(newButton, Pos.CENTER_LEFT);
            content.getChildren().add(anchorPane);
            scroller.vvalueProperty().bind(content.heightProperty());

            //Other Side
            anchorPane = new StackPane();
            anchorPane.setStyle("-fx-background-color:  transparent;");
           
            JFXButton RightButton = new JFXButton();
            RightButton.setGraphic(SentImage);
             RightButton.setPrefHeight(SentImage.getY());
            RightButton.setButtonType(JFXButton.ButtonType.FLAT);
            RightButton.setStyle("-fx-background-color:  transparent; -fx-text-fill: transparent;");
            //leftButton.setOpacity(1);
     
            anchorPane.getChildren().addAll(RightButton);
            anchorPane.setAlignment(RightButton, Pos.CENTER_RIGHT);
            content2.getChildren().add(anchorPane);
            scroller.vvalueProperty().bind(content2.heightProperty());
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }

    private void scrollToBottom(JScrollPane scrollPane) {
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }

}
