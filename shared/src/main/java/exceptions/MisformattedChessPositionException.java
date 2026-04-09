package exceptions;

public class MisformattedChessPositionException extends Exception {
    public MisformattedChessPositionException(String message) {
        super(message);
    }
    public MisformattedChessPositionException(String message, Throwable ex) { super(message, ex);}
}