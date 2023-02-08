package pl.envelo.moovelo.exception;

public class UnauthorizedRequestException extends RuntimeException {

    public UnauthorizedRequestException(String message) {
        super(message);
    }

    public UnauthorizedRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
