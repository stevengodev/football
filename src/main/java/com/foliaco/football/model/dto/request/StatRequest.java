package com.foliaco.football.model.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatRequest {

    private String mvp;
    private int goals;
    private int assists;
    private int shotsOnTarget;
    private int totalShots;
    private double shotsOnTargetPercentage;
    private int foulsCommitted;
    private int yellowCards;
    private int redCards;
    private int minutesPlayed;
    private int localTeamGoals;
    private int visitingTeamGoals;
    private int completedPassesLocalTeam;
    private int totalPassesVisitingTeam;
}
