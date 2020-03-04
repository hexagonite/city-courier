package pl.ug.citycourier.rest.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.ug.citycourier.internal.security.internal.exception.BindingResultException;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(BindingResultException.class)
    public ResponseEntity handleBindingResultException(BindingResultException exception) {
        return new ResponseEntity(exception.getErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAllExceptions(Exception e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
