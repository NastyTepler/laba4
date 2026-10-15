package exceptions;

public class InvalidFigureException extends RuntimeException {

    public InvalidFigureException(String message) {
        super(message);
    }

    public InvalidFigureException(String message, Throwable cause) {
        super(message, cause);
    }
}