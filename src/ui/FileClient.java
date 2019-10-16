package ui;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileClient extends Thread{

    FileClient()
    {
        start();
    }
    @Override
   public void run()  {
       while(true){
        try {
            int filesize = 500000000;
            int bytesRead;
            int currentTot = 0;
            
            Socket socket = new Socket("localhost", 1111);
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = "";
            message = br.readLine();
            System.out.println("Message received from the server : " +message);
            socket.close();
            
           
            socket = new Socket("localhost", 1112);
            byte[] bytearray = new byte[filesize];
            is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream("C:\\Users\\altam\\Desktop\\Copy."+message);
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
            
        } catch (Exception ex) {
            Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    }
}
