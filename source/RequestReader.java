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
public String path ="";
public String httpvers ="";


public RequestReader(String r) throws Exception{
    String[] request = r.split("");

    if (request.length >= 3 ){
        if (request[0].equals(HTTP.GET)){
            
            methode = HTTP.GET ;

            if (!request[1].isEmpty()){
                
                
                try {
                    File filepath = new File(request[1]);
                    if (filepath.isDirectory()){
                        contenttype = "indexfile";

                    }else if ((filepath.isFile() && filepath.canRead() && 
                    (contenttype = Files.probeContentType(filepath.toPath())) != null)){
                        path = request[1];
                        httpvers = request[2];
                    }else{
                        throw HTTP.InternalServerErrorException; 
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