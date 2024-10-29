package source;
public class main {

    public static void main(String[] args) {
        try {
        RequestReader r = new RequestReader("GET /movie_p4.cpk HTTP/1.1");
        System.out.println(r.extension +"\n"+r.contenttype+"\n"+r.httpvers+"\n"+r.methode+"\n"+r.path);
       }catch(Exception e){
        e.printStackTrace();
       }}
       
}
