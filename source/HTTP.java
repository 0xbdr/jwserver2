package source;

public class HTTP {

    public static final int OK = 200;
    public static final int NOTFOUND = 404;
    public static final int INTERNALSERVERERROR = 500;
    public static final String GET = "GET";
    public static final String POST = "POST";
    

    
    
    public static Exception NotFoundException = new Exception(){
        @Override
    public String getMessage() {
        return "404 File not found!";
    }
    };

    public static Exception InternalServerErrorException = new Exception(){
        @Override
    public String getMessage() {
        return "500 Internal Server error";
    }
    };

    public static Exception BadRequestException = new Exception(){
        @Override
    public  String getMessage() {
        return "400 BadRequest";
    }
    };
}
