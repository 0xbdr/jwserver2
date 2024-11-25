package source;
import java.io.File;
import java.nio.file.*;
public class RequestReader {
    
    /**
 * A class for reading the request 
 * 
 */
public String methode ="";
public String contenttype ="";
public String filename = "";
public String path ="";
public String httpvers ="";


public RequestReader(String r) throws Exception{
    String[] request = r.split(" ");

    if (request.length >= 3 ){
        if (request[0].equals(HTTP.GET)){
            
            methode = HTTP.GET ;

            if (!request[1].isEmpty()){
                
                
                try {
                    File filepath = new File("files/"+request[1]);
                    if (filepath.isDirectory()){
                        contenttype = "indexfile";
                        path = "files/" + request[1];
                        httpvers = request[2];

                    }else if (filepath.isFile() && filepath.canRead())  {
                        filename = filepath.getName();
                    if ((contenttype = Files.probeContentType(filepath.toPath())) == null){
                        contenttype = "application/octet-stream";
                    }
                        path = "files/" + request[1];
                        httpvers = request[2];
                    }else if (!filepath.exists()){
                        path =String.valueOf(HTTP.NOTFOUND);
                    }else{
                        path = String .valueOf(HTTP.INTERNALSERVERERROR);
                    }


                }catch(Exception e ){

                }
                

        }else{throw HTTP.BadRequestException;}

    }else if (request[0].equals(HTTP.POST)){


    }

    }else {
        throw HTTP.BadRequestException;
    }
    
    

}
}