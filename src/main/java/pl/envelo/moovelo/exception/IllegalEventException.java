package pl.envelo.moovelo.exception;

public class IllegalEventException extends RuntimeException {
    public IllegalEventException(String message) {
        super(message);
    }

    public IllegalEventException(String message, Throwable cause) {
        super(message, cause);
    }
}
