package com.foliaco.football.model.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchRequest {

    private String mvp;
    private String season;
    private String competition;
    private String localTeamId;
    private String visitingTeamId;
    private LocalDateTime dateTime;
    private String location;
    private String stadium;

}
