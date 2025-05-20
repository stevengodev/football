package com.foliaco.football.model.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerRequest {

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
