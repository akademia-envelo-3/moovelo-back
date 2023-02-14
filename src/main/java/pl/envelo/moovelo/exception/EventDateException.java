package pl.envelo.moovelo.exception;

public class EventDateException extends RuntimeException {
    public EventDateException(String message) {
        super(message);
    }

    public EventDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
