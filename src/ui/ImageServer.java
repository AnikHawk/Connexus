
package ui;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.Texting.ImageClient;

  public class ImageServer extends Thread{
    String path;
    ImageServer(String path) throws Exception{
        this.path = path;
        start();
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            Socket socket = serverSocket.accept();
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            File f = new File(path);
            // System.out.println(f.getName());
            String fileName = f.getName();
            String fileExtension = fileName.substring(fileName.indexOf(".") + 1, f.getName().length());
            bw.write(fileExtension);
            System.out.println("Message sent to the client is " + path);
            bw.flush();
            socket.close();
            serverSocket.close();
            
            serverSocket = new ServerSocket(9998);
            socket = serverSocket.accept();
            System.out.println("Accepted connection : " + socket);
            
            File transferFile = new File(path);
            FileInputStream fin = new FileInputStream(transferFile);
            BufferedInputStream bin = new BufferedInputStream(fin);
            
            byte[] bytearray = new byte[(int) transferFile.length()];
            System.out.println(bytearray.length);
            bin.read(bytearray, 0, bytearray.length);
            os = socket.getOutputStream();
            System.out.println("Sending Files...");
            os.write(bytearray, 0, bytearray.length);
            os.flush();
            
            socket.close();
            serverSocket.close();
            System.out.println("File transfer complete");
        } catch (Exception ex) {
            Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    