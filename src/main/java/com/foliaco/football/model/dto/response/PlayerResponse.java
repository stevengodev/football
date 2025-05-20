package com.foliaco.football.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerResponse {
    private String name;
    private int bib;
    private String nationality;
    private String position;
    private LocalDate birthDate;
    private String photoUrl;
    private int goals;
    private int assists;
    private int trophies;

}
