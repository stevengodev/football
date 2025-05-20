package com.foliaco.football.exception;

import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler({ InvalidDataException.class , NotFoundException.class } )
    public ResponseEntity<Error> handleInvalidDataException(InvalidDataException ex,
                                                            HttpRequest request){

        LocalDateTime dateTime = LocalDateTime.now();
        String formattedDateTime = dateTime.format(formatter);

        Error error = Error.builder()
                .message(ex.getMessage())
                .httpMethod(request.getMethod())
                .status(BAD_REQUEST.value())
                .path(request.getURI().getPath())
                .dateTime( LocalDateTime.parse(formattedDateTime) )
                .build();

        return new ResponseEntity<>(error, BAD_REQUEST);

    }

}
