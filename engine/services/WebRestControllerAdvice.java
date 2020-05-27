package engine.services;

import org.jboss.jandex.Index;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class WebRestControllerAdvice {

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public String handleIndexOutOfBoundsException(IndexOutOfBoundsException ex) {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Not found"
        );
    }
}
