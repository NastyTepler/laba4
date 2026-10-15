package exceptions;

public class StorageException extends Exception {

    private final int lineNumber;

    public StorageException(String message) {
        super(message);
        this.lineNumber = -1;
    }

    public StorageException(int lineNumber, String message) {
        super(message);
        this.lineNumber = lineNumber;
    }

    public StorageException(int lineNumber, String message, Throwable cause) {
        super(message, cause);
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}