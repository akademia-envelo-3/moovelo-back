package pl.envelo.moovelo.exception;

public class StatusNotExistsException extends RuntimeException {

    public StatusNotExistsException(String message) {
        super(message);
    }

    public StatusNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
