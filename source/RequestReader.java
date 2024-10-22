package source;
import java.io.File;
import java.nio.file.*;;
public class RequestReader {
    
    /**
 * A class for reading the request 
 * 
 */
public String methode ="";
public String filetype ="";
public String path ="";
public String extension ="";
public String httpvers ="";


public RequestReader(String r){
    String[] resulte = r.split(" ");

   if (resulte[0].equals(HTTP.GET)){
         if (resulte[2].contains("script")|| r.contains(".js")){
            resulte[1] = "script";
            
        }else if (r.contains("style")|| r.contains("css")){
            resulte[1] = "style";
           
        }else if (r.contains(".png") || r.contains(".jpg") || r.contains(".jpeg")|| r.contains(".svg")|| r.contains(".gif")||r.contains(".bmp")||r.contains(".webp")||r.contains(".ico")){
            resulte[1] ="img";
            if (r.contains(".png")) {
                resulte[3] = "png";
            }else if(r.contains(".jpg")){
                resulte[3] = "jpg";
            }else if (r.contains(".jpeg")) {
                resulte[3] = "jpg";
            }else if ( r.contains(".svg")) {
                resulte[3] = "svg+xml";
            }else if (r.contains(".gif")) {
                resulte[3] = "gif";
            }else if (r.contains(".bmp")) {
                resulte[3] = "bmp";
            }else if (r.contains(".webp")) {
                resulte[3] = "webp";
            }else if (r.contains(".ico")) {
                resulte[3] = "ico";
            }
            
        }else{
            File file = new File("files/" + resulte[2]);
            if (file.exists() && !file.isDirectory()){
                resulte[1]="download";resulte[3] = file.getName();}
            else {
            if (file.isDirectory()) {resulte[1]="indexfile";}
            }
        }
    }else if (resulte[0].equals(HTTP.POST)){




    }
       
        if (resulte[1].isEmpty()){
            resulte[1] = String.valueOf(HTTP.notfound);
        }
      
    }

  
   
}