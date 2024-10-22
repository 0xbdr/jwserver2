package source;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
public class Tools {

    
    

public static boolean Streambuffer(File f,OutputStream os){
    try {
    FileInputStream fis = new FileInputStream(f);
    BufferedInputStream binst = new BufferedInputStream(fis);
    BufferedOutputStream bost = new BufferedOutputStream(os);
    byte[] fbyte = new byte[8192] ;
    int bread;
    while ((bread = binst.read(fbyte))!= -1) {
        bost.write(fbyte, 0, bread);
        bost.flush();
    }
    bost.flush();
    return true;
}catch(Exception e){

    System.err.println(e.getClass().getName() + " connection error");
    return false;

        }
    }

    public static byte[] getbuffer(String p) {
        File file = new File(p);
        if (!file.exists()){return null;}
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
    
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String FileString(String path){
        StringBuilder res = new StringBuilder() ;
        try {
        File f = new File(path);
        BufferedReader r =new BufferedReader(new FileReader(f));
        String l ;
        
        while ((l = r.readLine()) != null) {
            res.append(l).append("\n");
            
        }r.close();
    }catch(Exception e){
            return null;
        }
        return res.toString();

        }
    }