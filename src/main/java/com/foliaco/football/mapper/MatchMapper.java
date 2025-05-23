package com.foliaco.football.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.foliaco.football.dao.repository.StatRepository;
import com.foliaco.football.model.document.Match;
import com.foliaco.football.model.dto.request.MatchRequest;
import com.foliaco.football.model.dto.response.MatchResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchMapper {

    @Mapping(source = "season", target = "season")
    @Mapping(source = "competition", target = "competition")
    @Mapping(source = "localTeamId", target = "localTeamId")
    @Mapping(source = "visitingTeamId", target = "visitingTeamId")
    @Mapping(source = "dateTime", target = "dateTime")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "stadium", target = "stadium")
    Match toMatch(MatchRequest request);

    @Mapping(source = "season", target = "season")
    @Mapping(source = "competition", target = "competition")
    @Mapping(source = "localTeamId", target = "localTeamId")
    @Mapping(source = "visitingTeamId", target = "visitingTeamId")
    @Mapping(source = "dateTime", target = "dateTime")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "stadium", target = "stadium")
    MatchResponse toMatchResponse(Match match);

    
    List<MatchResponse> toMatchesResponse(List<Match> matchList);
    MatchRequest toMatchRequest(Match match);
    MatchResponse toMatchResponse(MatchRequest matchRequest);
    
}
