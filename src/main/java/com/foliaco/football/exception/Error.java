package com.foliaco.football.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    private String message;
    private HttpMethod httpMethod;
    private int status;
    private String path;
    private LocalDateTime dateTime;

}
