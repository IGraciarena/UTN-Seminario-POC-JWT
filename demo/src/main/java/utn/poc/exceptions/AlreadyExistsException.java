package utn.poc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String message){
        super(message);
    }
}
