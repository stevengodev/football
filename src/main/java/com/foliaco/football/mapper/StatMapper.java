
package com.foliaco.football.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.foliaco.football.model.document.Stat;
import com.foliaco.football.model.dto.request.StatRequest;
import com.foliaco.football.model.dto.response.StatResponse;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StatMapper {

    @Mapping(source = "mvp", target = "mvp")
    @Mapping(source = "shotsOnTargetLocalTeam", target = "shotsOnTargetLocalTeam")
    @Mapping(source = "shotsOnTargetVisitingTeam", target = "shotsOnTargetVisitingTeam")
    @Mapping(source = "totalShotsLocalTeam", target = "totalShotsLocalTeam")
    @Mapping(source = "totalShotsVisitingTeam", target = "totalShotsVisitingTeam")
    @Mapping(source = "localTeamGoals", target = "localTeamGoals")
    @Mapping(source = "visitingTeamGoals", target = "visitingTeamGoals")
    @Mapping(source = "completedPassesLocalTeam", target = "completedPassesLocalTeam")
    @Mapping(source = "completedPassesVisitingTeam", target = "completedPassesVisitingTeam")
    @Mapping(source = "totalPassesLocalTeam", target = "totalPassesLocalTeam")
    @Mapping(source = "totalPassesVisitingTeam", target = "totalPassesVisitingTeam")
    @Mapping(source = "localTeamFouls", target = "localTeamFouls")
    @Mapping(source = "visitingTeamFouls", target = "visitingTeamFouls")
    @Mapping(source = "localTeamYellowCards", target = "localTeamYellowCards")
    @Mapping(source = "visitingTeamYellowCards", target = "visitingTeamYellowCards")
    @Mapping(source = "localTeamRedCards", target = "localTeamRedCards")
    @Mapping(source = "visitingTeamRedCards", target = "visitingTeamRedCards")
    @Mapping(source = "localTeamOffsides", target = "localTeamOffsides")
    @Mapping(source = "visitingTeamOffsides", target = "visitingTeamOffsides")
    @Mapping(source = "localTeamCorners", target = "localTeamCorners")
    @Mapping(source = "visitingTeamCorners", target = "visitingTeamCorners")
    @Mapping(source = "localTeamPossession", target = "localTeamPossession")
    @Mapping(source = "visitingTeamPossession", target = "visitingTeamPossession")
    Stat toStat(StatRequest request);

    StatResponse toStatResponse(Stat stat);
    List<StatResponse> toStatsResponse(List<Stat> stats);
    
}