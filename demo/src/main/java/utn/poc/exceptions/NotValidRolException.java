package utn.poc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class NotValidRolException extends RuntimeException {

    public NotValidRolException(String message){
        super(message);
    }
}
