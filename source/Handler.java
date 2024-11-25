package source;

import java.net.Socket;
   /**
    * Source.Handler
 * used for handling client requests , or errors (Source.HTTP Exceptons)
 *  
 * use :
 * 
 * Webserver.SetRequestHandler(new Handler(){
 *  @Override
        public  void handleRequest(RequestReader reader,Socket clientSocket) throws Exception{
            //your code here 
        }
        @Override
        public void handleError(Exception e) {
            //leave
        }});
 * or :
 * Webserver.SetErrorHandler(new Handler(){
 *  @Override
        public  void handleRequest(RequestReader reader,Socket clientSocket) throws Exception{
             //leave
        }
        @Override
        public void handleError(Exception e) {
        //your code here
        }});
 */
public interface Handler {
    /**
     * this should return HTTP.OK or HTTP.NOTFOUND or HTTP.INTERNALSERVERERROR
 */
    public abstract int handleRequest(RequestReader reader,Socket clientSocket) throws Exception;
    public abstract void handleError(Exception e, Socket clientSocket) ;

    
    
}
