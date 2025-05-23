package com.foliaco.football.exception;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler({ InvalidDataException.class })
    public ResponseEntity<Error> handleInvalidDataException(InvalidDataException ex,
            HttpServletRequest request) {

        LocalDateTime dateTime = LocalDateTime.now();
        String formattedDateTime = dateTime.format(formatter);

        Error error = Error.builder()
                .message(ex.getMessage())
                .httpMethod(HttpMethod.valueOf(request.getMethod()))
                .status(BAD_REQUEST.value())
                .path(request.getRequestURI())
                .dateTime(LocalDateTime.parse(formattedDateTime))
                .build();

        return new ResponseEntity<>(error, BAD_REQUEST);

    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Error> handleNotFoundException(NotFoundException ex,
            HttpServletRequest request) {

        LocalDateTime dateTime = LocalDateTime.now();
        String formattedDateTime = dateTime.format(formatter);

        Error error = Error.builder()
                .message(ex.getMessage())
                .httpMethod(HttpMethod.valueOf(request.getMethod()))
                .status(BAD_REQUEST.value())
                .path(request.getRequestURI())
                .dateTime(LocalDateTime.parse(formattedDateTime))
                .build();

        return new ResponseEntity<>(error, BAD_REQUEST);

    }

}
