package pl.envelo.moovelo.exception;

public class VisitorAlreadyExistsException extends RuntimeException {
    public VisitorAlreadyExistsException(String message) {
        super(message);
    }

    public VisitorAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
