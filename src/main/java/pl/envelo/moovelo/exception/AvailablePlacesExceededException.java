package pl.envelo.moovelo.exception;

public class AvailablePlacesExceededException extends RuntimeException {
    public AvailablePlacesExceededException(String message) {
        super(message);
    }

    public AvailablePlacesExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
