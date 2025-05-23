package com.foliaco.football.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foliaco.football.service.StatService;
import com.foliaco.football.model.dto.response.StatResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/stats")
@AllArgsConstructor
public class StatController {

    private final StatService statService;

    @GetMapping("/{id}")
    public ResponseEntity< StatResponse > getStatById(@PathVariable String id) {
        return ResponseEntity.ok(statService.getStatById(id));
    }


}
