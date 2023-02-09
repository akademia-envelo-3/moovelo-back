package pl.envelo.moovelo.exception;

public class NoContentException extends RuntimeException {

    public NoContentException(String message) {
        super(message);
    }

    public NoContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
