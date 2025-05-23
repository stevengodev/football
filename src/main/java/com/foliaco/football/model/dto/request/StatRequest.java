package com.foliaco.football.model.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatRequest {

    private String mvp;

    private int shotsOnTargetLocalTeam;

    private int shotsOnTargetVisitingTeam;

    private int totalShotsLocalTeam;

    private int totalShotsVisitingTeam;

    private int localTeamGoals;

    private int visitingTeamGoals;

    private int completedPassesLocalTeam;

    private int completedPassesVisitingTeam;

    private int totalPassesLocalTeam;

    private int totalPassesVisitingTeam;

    private int localTeamFouls;

    private int visitingTeamFouls;

    private int localTeamYellowCards;

    private int visitingTeamYellowCards;

    private int localTeamRedCards;

    private int visitingTeamRedCards;

    private int localTeamOffsides;

    private int visitingTeamOffsides;

    private int localTeamCorners;

    private int visitingTeamCorners;

    private int localTeamPossession;

    private int visitingTeamPossession;

}
