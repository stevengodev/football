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
public class MatchResponse {

    private String mvp;
    private String season;
    private String competition;
    private String localTeamId;
    private String visitingTeamId;
    private LocalDate dateTime;
    private String location;
    private String stadium;

}
