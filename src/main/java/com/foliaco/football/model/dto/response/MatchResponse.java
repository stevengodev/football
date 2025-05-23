package com.foliaco.football.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResponse {

    private String season;
    private String competition;
    private String localTeamId;
    private String visitingTeamId;
    private LocalDateTime dateTime;
    private String location;
    private String stadium;
    private StatResponse statResponse;

}
