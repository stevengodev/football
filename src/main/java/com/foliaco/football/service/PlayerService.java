package com.foliaco.football.service;

import com.foliaco.football.model.dto.request.PlayerRequest;
import com.foliaco.football.model.dto.request.SearchCriteria;
import com.foliaco.football.model.dto.response.DataPaginator;
import com.foliaco.football.model.dto.response.PlayerResponse;

import java.util.List;
import java.util.Optional;

/**
 * Interface that defines the services available for player management.
 * Provides basic CRUD operations, filtered searching, and player pagination.
 */
public interface PlayerService {

    /**
     * Create a new player from the data provided in the request.
     *
     * @param request PlayerRequest object containing the data for the player to create
     * @return PlayerResponse with the data for the created player, including their ID
     */
    PlayerResponse createPlayer(PlayerRequest request);

    /**
     * Update the data of an existing player identified by their ID.
     *
     * @param id Unique identifier of the player to update
     * @param request PlayerRequest object with the new player data
     * @return PlayerResponse with the updated player data
     */
    PlayerResponse updatePlayer(String id, PlayerRequest request);

    /**
     * Remove an existing player identified by their ID.
     *
     * @param id Unique identifier of the player to remove
     */
    void deletePlayer(String id);

    /**
     * Get data for a specific player by their ID.
     *
     * @param id Unique identifier of the player to query
     * @return Optional containing the PlayerResponse if the player is found, or empty if the player does not exist
     */
    Optional<PlayerResponse> getPlayerById(String id);
    /**
     * Get a paginated list of players.
     *
     * @param page Page number to retrieve (starting from 0)
     * @param size Number of players per page
     * @return List of PlayerResponse players on the requested page
     * @throws IllegalArgumentException if page or size are negative values
     */
    List<PlayerResponse> getPlayersPaginated(int page, int size);


    /**
     * Calculate the total number of pages available for player pagination.
     *
     * @param size Number of players per page
     * @return Total number of pages available
     */
    int getTotalPagesPlayers(int size);

    /**
     * Get a list of players that meet the specified search criteria.
     *
     * @param searchesCriteria List of search criteria to filter players against
     * @return List of PlayerResponse that meet the search criteria
     */
    List<PlayerResponse> getPlayersByFilter(List<SearchCriteria> searchesCriteria);

    /**
     * Get a paginator object with player pagination information.
     *
     * @param page Page number to retrieve (starting from 0)
     * @param size Number of players per page
     * @return DataPaginator containing the player list and pagination information
     */
    DataPaginator<List<PlayerResponse>> getPlayersDataPaginator(int page, int size);

}
