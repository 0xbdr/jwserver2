package source;
public class main {

    public static void main(String[] args) {
        try {
        RequestReader r = new RequestReader("GET ");
        System.out.println(r.contenttype+"\n"+r.httpvers+"\n"+r.methode+"\n"+r.path);
       }catch(Exception e){
        e.printStackTrace();
        
       }}
       
}
