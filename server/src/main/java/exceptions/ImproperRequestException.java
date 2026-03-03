package exceptions;

public class ImproperRequestException extends Exception {
    public ImproperRequestException(String message) {
        super(message);
    }
    public ImproperRequestException(String message, Throwable ex) { super(message, ex);}
}