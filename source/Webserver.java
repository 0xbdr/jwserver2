package source;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Webserver {
    public final static int PORT = 80;
    public static boolean exit = false;
    
    
    
    

    public static void run() {
        try {
            ServerSocket server = new ServerSocket(PORT); 
            System.out.println("|| HTTP Server started on port " + PORT+" ||");
            while (true) {
                Socket client = server.accept();
                new Thread(() -> handleRequest(client)).start();;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while starting the server");
        }
    }

    public static void handleRequest(Socket clientSocket) {

        
        String r ="";
        String threadresult = "";
        
        try {




            BufferedReader reqbufr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            String response = "";
            String request =reqbufr.readLine();


            threadresult += "===================================\n"
            +"Client  :: "+clientSocket.getInetAddress().getHostAddress() + 
            "\n" +"Request :: " + request +"\n";


            if (request  != null && !request.isEmpty()){

                RequestReader Requestreader = new source.RequestReader(request);
                threadresult +="Methode :: "+Requestreader.methode+"\nPath    :: "+Requestreader.path +"\n";

                 if(Requestreader.methode.equals(HTTP.GET)){


                                if (Requestreader.filetype.equals("indexfile") || 
                                Requestreader.filetype.equals("style") || 
                                Requestreader.filetype.equals("script")){
                                    
                                    if(Requestreader.filetype.equals("indexfile")){
                                        if ((response = Tools.FileString( Requestreader.path + "index.html"))== null){throw HTTP.NotFoundException;};
                                    }else{
                                        if ((response = Tools.FileString( Requestreader.path ))== null){throw HTTP.NotFoundException;};
                                    }
                                    
                                    out.println("HTTP/1.1 200 OK");
                                    r = "200 OK";
                                    switch (Requestreader.filetype) {
                                        case "indexfile":
                                            out.println("Content-Type: text/html");
                                            break;
                                        case "style":
                                            out.println("Content-Type: style/css");
                                            break;
                                        case "script":
                                            out.println("Content-Type: text/javascript");break; }
                                    
                                    out.println("Content-Length: " + response.length());
                                    out.println();
                                    out.print(response);
                                    out.flush();

                                }
                                else if (Requestreader.filetype.equals("img") || Requestreader.filetype.equals("ico")){
                                    byte[] buffer;
                                    if ((buffer= Tools.getbuffer(Requestreader.path))== null){throw HTTP.NotFoundException;};
                                    out.println("HTTP/1.1 200 OK");
                                    r = "200 OK";
                                    out.println("Content-Type: image/"+Requestreader.extension);
                                    out.println("Content-Length: " + buffer.length);
                                    out.println();
                                    clientSocket.getOutputStream().write(buffer);
                                    out.flush();

                                }
                                else if (Requestreader.filetype.equals("download")){
                                    File fl = new File(Requestreader.path);
                                    out.println("HTTP/1.1 200 OK");
                                    r = "200 OK";
                                    out.println("Content-Type: application/octet-stream");  
                                    out.println("Content-Length: " + fl.length());
                                    out.println("Content-Disposition: attachment"); // the Rreader.extension will return the full file name
                                    out.println("filename=\"" + Requestreader.extension + "\"");
                                    if ( ! Tools.Streambuffer(fl, clientSocket.getOutputStream())){
                                        throw HTTP.NotFoundException;
                                    }
                                    out.flush();
                                
                                
                                    
                                }else if (Requestreader.filetype.equals(String.valueOf(HTTP.notfound))){
                                    throw HTTP.NotFoundException;

                                }

                }else if (Requestreader.methode.equals(HTTP.POST)){
                    //TODO add post request hh

                }
             
             
             
             
                r+=" : " +Requestreader.filetype;
             }else {
                throw HTTP.BadRequestException;
             }
        
            
              
            
          } catch (Exception e) {
            System.out.println(e.getClass().getName());

                    if (e.getClass() == HTTP.NotFoundException.getClass()){
                        r = "404 NotFound";
                        try {
                            clientSocket.getOutputStream().write("HTTP/1.1 404 Not Found".getBytes());
                        } catch (Exception ex) {
                            
                        }
                        
                    }else if (e.getClass() == HTTP.BadRequestException.getClass()){
                        try {
                            clientSocket.getOutputStream().write("400 Bad Request".getBytes());
                        } catch (Exception ex) {
                            
                        }
                    }
                
                    
                
        }finally{
                try{
                    System.out.println(threadresult+"Status  :: "+r);
                    
                
                clientSocket.close();  }
                catch(Exception e){
                    e.printStackTrace();}
        }
    }
    

}
