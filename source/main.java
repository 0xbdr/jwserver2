package source;
public class main {

    public static void main(String[] args) {
        
        RequestReader r = new RequestReader("GET /movie_p4.cpk HTTP/1.1");
        System.out.println(r.extension +"\n"+r.filetype+"\n"+r.httpvers+"\n"+r.methode+"\n"+r.path);
       }
       
}
