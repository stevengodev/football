package com.foliaco.football.service.impl;

import com.foliaco.football.dao.repository.PlayerRepository;
import com.foliaco.football.exception.NotFoundException;
import com.foliaco.football.mapper.PlayerMapper;
import com.foliaco.football.model.document.Player;
import com.foliaco.football.model.dto.request.PlayerRequest;
import com.foliaco.football.model.dto.request.SearchCriteria;
import com.foliaco.football.model.dto.response.DataPaginator;
import com.foliaco.football.model.dto.response.PlayerResponse;
import com.foliaco.football.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    private final PlayerMapper playerMapper;

    private final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Override
    public PlayerResponse createPlayer(PlayerRequest request) {
        Player player = playerRepository.save( playerMapper.toPlayer(request) );
        logger.info("Created player: {}", player);
        return playerMapper.toPlayerResponse(player);
    }

    @Override
    public PlayerResponse updatePlayer(String id, PlayerRequest request) {

        Player player = playerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Player not found with id " + id)
        );

        player.setName(request.getName());
        player.setBib(request.getBib());
        player.setNationality(request.getNationality());
        player.setPosition(request.getPosition());
        player.setBirthDate(request.getBirthDate());
        player.setPhotoUrl(request.getPhotoUrl());
        player.setGoals(request.getGoals());
        player.setAssists(request.getAssists());
        player.setTrophies(request.getTrophies());

        logger.info("Updated player: {}", player);

        return playerMapper.toPlayerResponse( playerRepository.save(player) );

    }

    @Override
    public void deletePlayer(String id) {
        playerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Player not found with id " + id)
        );

        logger.info("Deleted player with id: {}", id);

        playerRepository.deleteById(id);

    }

    @Override
    public Optional<PlayerResponse> getPlayerById(String id) {

        if (playerRepository.findById(id).isEmpty()){
            return Optional.empty();
        }

        logger.info("Found player with id: {}", id);

        return playerRepository.findById(id).map(playerMapper::toPlayerResponse);

    }

    @Override
    public List<PlayerResponse> getPlayersPaginated(int page, int size) {
        logger.info("Getting players paginated");
        return playerMapper.toPlayersResponse(playerRepository.getPlayersPaginated(page, size));
    }

    @Override
    public int getTotalPagesPlayers(int size) {
        logger.info("Getting total pages of players");
        return playerRepository.getTotalPagesPlayers(size);
    }

    @Override
    public List<PlayerResponse> getPlayersByFilter(List<SearchCriteria> searchesCriteria){
        logger.info("Getting players by filter");
        List<Player> players = playerRepository.getPlayersByFilters(searchesCriteria);
        return playerMapper.toPlayersResponse(players);

    }

    @Override
    public DataPaginator<List<PlayerResponse>> getPlayersDataPaginator(int page, int size) {

        int totalPages = getTotalPagesPlayers(size);

        if (page < 0 || page >= totalPages) {
            throw new IllegalArgumentException("Page number out of range");
        }

        return new DataPaginator<>(getPlayersPaginated(page, size), totalPages, page);

    }

}
