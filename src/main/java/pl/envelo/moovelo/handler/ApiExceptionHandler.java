package pl.envelo.moovelo.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public final ResponseEntity<ErrorMessage> somethingWentWrong(Exception ex) {
        log.error("Exception: {} {}", ex.getMessage(), ex);
        ErrorMessage exceptionResponse = new ErrorMessage(ex.getMessage(), ex);

        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    class ErrorMessage {
        private String message;
        private Throwable throwable;
    }
}


