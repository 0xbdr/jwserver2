package source;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;;


public class Webserver {
    public static int port = 80;
    private static volatile boolean exit = false;
    private static int connections = 0;
    private static Runnable Errorhandler = new Runnable() {
        @Override
        public void run(){

    }};
    
    
    public static void changePort(int port){

    }
    public static void Stop(){
        exit = true;
    }
    public static boolean IsRunning(){
        return !exit;
    }
    public static int GetConnectionsCount(){
        return connections;
    }
    public static void SetErrorHandler(Runnable r){
        Errorhandler = r;
    }
    public static void run(){
        try  {
            Stop();
        Thread.sleep(1222);
        exit = false;
        new Thread(()-> start()).start();;

        }catch(Exception e){
            System.out.println("cant start the server :: " + e.getClass().getName());
            e.printStackTrace();
        }

    }
    

    public static void start() {
        try {
            ServerSocket server = new ServerSocket(port); 
            System.out.println("|| HTTP Server started on port " + port+" ||");
            while (true) {
                Socket client = server.accept();
                new Thread(() -> handleRequest(client)).start();;
                connections+=1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while starting the server");
        }
    }

    private static void handleRequest(Socket clientSocket) {

        
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


                                if (Requestreader.contenttype.equals("indexfile") || Requestreader.contenttype.equals("text/css") || 
                                Requestreader.contenttype.equals("text/javascript"))
                                
                                {
                                    
                                    if(Requestreader.contenttype.equals("indexfile")){
                                        if ((response = Tools.FileString( Requestreader.path + "index.html"))== null){throw HTTP.NotFoundException;};
                                    }else{
                                        if ((response = Tools.FileString( Requestreader.path ))== null){throw HTTP.NotFoundException;};
                                    }
                                    
                                    out.println("HTTP/1.1 200 OK");
                                    r = "200 OK";
                                    out.println("Content-Type: " + Requestreader.contenttype);
                                    out.println("Content-Length: " + response.length());
                                    out.println();
                                    out.print(response);
                                    out.flush();

                                }
                                else if (Requestreader.contenttype.contains("image/")){
                                    File img = new File(Requestreader.path);
                                    
                                    out.println("HTTP/1.1 200 OK");
                                    r = "200 OK";
                                    out.println("Content-Type: "+Requestreader.contenttype);
                                    out.println("Content-Length: " + img.length());
                                    out.println();
                                    Tools.Streambuffer(img, clientSocket.getOutputStream());
                                    out.flush();

                                }
                                else if (Requestreader.contenttype.equals("application/octet-stream")){

                                    File fl = new File(Requestreader.path);
                                    out.println("HTTP/1.1 200 OK");
                                    r = "200 OK";
                                    out.println("Content-Type: application/octet-stream");  
                                    out.println("Content-Length: " + fl.length());
                                    out.println("Content-Disposition: attachment"); 
                                    out.println("filename=\"" + Requestreader.contenttype + "\"");
                                    if ( ! Tools.Streambuffer(fl, clientSocket.getOutputStream())){
                                        throw HTTP.InternalServerErrorException;
                                    }
                                    out.flush();
                                
                                
                                    
                                }else if (Requestreader.contenttype.isEmpty() && Requestreader.contenttype.equals(String.valueOf(HTTP.NOTFOUND))){
                                    throw HTTP.NotFoundException;

                                }else if (Requestreader.contenttype.isEmpty() && Requestreader.path.equals(String.valueOf(HTTP.INTERNALSERVERERROR))){
                                    throw HTTP.InternalServerErrorException;
                                }

                }else if (Requestreader.methode.equals(HTTP.POST)){
                    //TODO add post request hh

                }
             
             
             
             
                r+=" : " +Requestreader.contenttype;
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
                            clientSocket.getOutputStream().write("HTTP/1.1 400 Bad Request".getBytes());
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
