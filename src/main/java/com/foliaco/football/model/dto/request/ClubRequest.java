package com.foliaco.football.model.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubRequest {

    private String name;
    private String country;
    private String league;
    private String president;
    private String acronym;
    private String badgeUrl;
    private LocalDate foundationDate;
    private String stadiumName;
    private String coachName;
    private int stadiumCapacity;

}
