package com.foliaco.football.mapper;

import com.foliaco.football.model.document.Player;
import com.foliaco.football.model.dto.request.PlayerRequest;
import com.foliaco.football.model.dto.response.PlayerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerMapper {

    Player toPlayer(PlayerRequest request);

    PlayerResponse toPlayerResponse(Player player);

    List<PlayerResponse> toPlayersResponse(List<Player> players);
}
