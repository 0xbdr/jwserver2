package source;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;;


public class Webserver {
    public static int port = 80;

    private static Handler Requesthandler= new Handler(){
        @Override
        public int handleRequest(RequestReader reader,Socket clientSocket) throws Exception{
            return DefaultRequestHandler(clientSocket, reader);
        }
        @Override
        public void handleError(Exception e,Socket clientsocket) {

        }
    };

    private static Handler Errorhandler = new Handler(){
        @Override
        public int handleRequest(RequestReader reader,Socket clientSocket) throws Exception{
            return 0;
        }
        @Override
        public void handleError(Exception e,Socket clientsocket) {
            DefaultErrorHandler(e, clientsocket);
        }
    };
    private static volatile boolean exit = false;
    private static int connections = 0;


   
    
    
    public static void changePort(int p){
        port = p;
        Stop();
        try {Thread.sleep(1024);}catch(Exception e){System.out.println("cant stop the thread");}
        run();

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
    public static void SetRequestHandler(Handler rh){
        Requesthandler = rh;
    }
    public static void SetErrorHandler(Handler eh){
        Errorhandler = eh;
    }
   
    
    public static void run(){
        exit = false ;
        try  {
            Stop();
        Thread.sleep(1222);
        exit = false;
        Thread thread = new Thread(()-> start());
        thread.start();;
        }catch(Exception e){
            System.out.println("cant start the server :: " + e.getClass().getName());
            e.printStackTrace();
        }

    }
    

    private static void start() {
        try {
            ServerSocket server = new ServerSocket(port); 
            System.out.println("|| HTTP Server started on port " + port+" ||");
            while (true) {
                Socket client = server.accept();
                new Thread(() -> ClientConnected(client)).start();;
                connections+=1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while starting the server");
        }
    }

    private static void ClientConnected(Socket clientSocket) {
        String r ="";
        String threadresult = "";
        
        try {
            BufferedReader reqbufr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String request =reqbufr.readLine();
            
            threadresult += "-------------ClientConnected-------------\n"+"Client  :: "+clientSocket.toString()
            +"\nClient  :: "+clientSocket.getInetAddress().getHostAddress() + "\nBuffer  :: "+String.valueOf(clientSocket.getReceiveBufferSize())+
            "\n" +"Request :: " + request +"\n";
            if (request  != null && !request.isEmpty()){
                RequestReader Requestreader = new source.RequestReader(request);
                threadresult +="Methode :: "+Requestreader.methode+"\nPath    :: "+Requestreader.path +"\n";
                r= String.valueOf(Requesthandler.handleRequest(Requestreader, clientSocket));
            }
                
                

        } catch (Exception e) {
           Errorhandler.handleError(e, clientSocket);
                
                    }finally{
                try{
                    System.out.println(threadresult+"Status  :: "+r);
                    clientSocket.close();  }
                catch(Exception e){
                    e.printStackTrace();}
        }
    }
    
    
    
    private static void DefaultErrorHandler(Exception e,Socket clientSocket){
        System.out.println("Exception !: " + e.getClass().getName());

                    if (e.getClass() == HTTP.NotFoundException.getClass()){
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
    }
    private static int DefaultRequestHandler(Socket clientSocket ,RequestReader Requestreader) throws Exception{
        
        String response = "";
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        int returnvalue = HTTP.NOTFOUND;

        if(Requestreader.methode.equals(HTTP.GET)){


            if (Requestreader.contenttype.equals("indexfile") 
            || Requestreader.contenttype.equals("text/css")
            || Requestreader.contenttype.equals("text/html") 
            || Requestreader.contenttype.equals("text/javascript")){
                
                if(Requestreader.contenttype.equals("indexfile")){
                    if ((response = Tools.FileString( Requestreader.path + "index.html"))== null){throw HTTP.NotFoundException;}
                }else{
                    if ((response = Tools.FileString( Requestreader.path ))== null){throw HTTP.NotFoundException;}
                }
                
                    out.println("HTTP/1.1 200 OK");
                    returnvalue = HTTP.OK;
                    out.println("Content-Type: " + Requestreader.contenttype);
                    out.println("Content-Length: " + response.length());
                    out.println();
                    out.print(response);
                    out.flush();

            }else if (Requestreader.contenttype.contains("image/")){
                File img = new File(Requestreader.path);
                
                out.println("HTTP/1.1 200 OK");
                returnvalue = HTTP.OK;
                out.println("Content-Type: "+Requestreader.contenttype);
                out.println("Content-Length: " + img.length());
                out.println();
                Tools.Streambuffer(img, clientSocket.getOutputStream());
                out.flush();

            }
            else if (Requestreader.contenttype.equals("application/octet-stream")){

                File fl = new File(Requestreader.path);
                out.println("HTTP/1.1 200 OK");
                returnvalue = HTTP.OK;
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
        return returnvalue;
 }
    }

  

