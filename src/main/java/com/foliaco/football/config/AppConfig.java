package com.foliaco.football.config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class AppConfig {

    @Bean
    ObjectMapper objectMapper(){
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule( new JavaTimeModule() );
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // objectMapper.setDateFormat( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") );
        return objectMapper;

    }

}
