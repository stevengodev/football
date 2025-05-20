package com.foliaco.football.mapper;

import com.foliaco.football.model.document.Club;
import com.foliaco.football.model.dto.request.ClubRequest;
import com.foliaco.football.model.dto.response.ClubResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClubMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "league", target = "league")
    @Mapping(source = "acronym", target = "acronym")
    @Mapping(source = "stadiumCapacity", target = "stadiumCapacity")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "coachName", target = "coachName")
    Club toClub(ClubRequest request);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "acronym", target = "acronym")
    @Mapping(source = "stadiumCapacity", target = "stadiumCapacity")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "coachName", target = "coachName")
    ClubResponse toClubResponse(Club club);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "acronym", target = "acronym")
    @Mapping(source = "stadiumCapacity", target = "stadiumCapacity")
    @Mapping(source = "country", target = "country")
    List<ClubResponse> toClubesResponse(List<Club> clubList);

}
