package exceptions;

public class NoGameException extends RuntimeException {
    public NoGameException(String message) {
        super(message);
    }
    public NoGameException(String message, Throwable ex) {
        super(message, ex);
    }
}
