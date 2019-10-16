package ui;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileServer extends Thread{
    String path;
    FileServer(String path) throws Exception{
        this.path = path;
        start();
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(1111);
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
            
            serverSocket = new ServerSocket(1112);
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

    public static void main(String[] args) throws IOException, Exception {

        new FileServer("E:\\a.mp3");
    }
}
