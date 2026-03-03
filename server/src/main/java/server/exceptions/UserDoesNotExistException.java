package server.exceptions;

public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(String message) {
        super(message);
    }
    public UserDoesNotExistException(String message, Throwable ex) {
        super(message, ex);
    }
}
