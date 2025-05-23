package com.foliaco.football.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubResponse {

    private String name;
    private String country;
    private String league;
    private String president;
    private String acronym;
    private String badgeUrl;
    private String foundationDate;
    private String stadiumName;
    private String coachName;
    private int stadiumCapacity;

}
