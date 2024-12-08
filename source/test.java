package source;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.*;
public class test {
    public static void main(String[] args,Socket clientSocket) {
        try {
           byte[] inbytes = new byte[1024];
            BufferedInputStream bin = new BufferedInputStream(clientSocket.getInputStream());
            String re ="";
            int read = 0;
            while ((read = bin.read(inbytes)) != -1) {
                re += inbytes.toString();
                
            }
            System.out.println(re);
        } catch (Exception e) {
            
        }
        
    }
    
}
