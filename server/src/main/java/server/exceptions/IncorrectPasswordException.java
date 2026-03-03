package server.exceptions;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException(String message) {
        super(message);
    }

    public UserDoesNotExistException(String message, Throwable ex) {
        super(message, ex);
    }
}
