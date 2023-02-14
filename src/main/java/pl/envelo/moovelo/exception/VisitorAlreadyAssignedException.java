package pl.envelo.moovelo.exception;

public class VisitorAlreadyAssignedException extends RuntimeException {

    public VisitorAlreadyAssignedException(String message) {
        super(message);
    }

    public VisitorAlreadyAssignedException(String message, Throwable cause) {
        super(message, cause);
    }
}
