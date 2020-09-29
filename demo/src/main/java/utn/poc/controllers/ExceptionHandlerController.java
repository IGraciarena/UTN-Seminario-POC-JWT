package utn.poc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import utn.poc.dto.ErrorDtoResponse;
import utn.poc.exceptions.AlreadyExistsException;
import utn.poc.exceptions.NotFoundException;
import utn.poc.exceptions.NotValidRolException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDtoResponse> handleAlreadyExistsException(AlreadyExistsException exception){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDtoResponse.builder()
                .message(exception.getMessage())
                .exception("AlreadyExistsException")
                .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDtoResponse> handleNotFoundException(NotFoundException exception){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDtoResponse.builder()
                .message(exception.getMessage())
                .exception("NotFoundException")
                .build());
    }

    @ExceptionHandler(NotValidRolException.class)
    public ResponseEntity<ErrorDtoResponse> handleNotValidRolException(NotValidRolException exception){

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ErrorDtoResponse.builder()
                .message(exception.getMessage())
                .exception("NotValidRolException")
                .build());
    }
}
