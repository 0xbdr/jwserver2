package source;
import java.io.File;
import java.nio.file.*;
public class test {
    public static void main(String[] args) {
        try {
            File fl = new File("files/");
            if (fl.isDirectory()){
                System.out.println("its a directory");
                for (File f : fl.listFiles()) {
                    if (f.isFile()){
                        System.out.println("file :: " + f.getName() + "; content type :: " + Files.probeContentType(f.toPath()));
                    }
                    
                }
                
                
            }
        } catch (Exception e) {
            
        }
        
    }
    
}
