package com.foliaco.football.service;

import com.foliaco.football.model.dto.request.ClubRequest;
import com.foliaco.football.model.dto.response.ClubResponse;
import com.foliaco.football.model.dto.response.DataPaginator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Interface that defines the services available for club management.
 * Provides basic CRUD operations as well as methods for paging and querying clubs.
 */
public interface ClubService {

    /**
     * Create a new club from the data provided in the request.
     *
     * @param request ClubRequest object containing the data needed to create a club
     * @return ClubResponse with the data for the created club, including its unique identifier
     */
    ClubResponse createClub(ClubRequest request);

    /**
     * Update the data for an existing club identified by its ID.
     *
     * @param id Unique identifier of the club to update
     * @param request ClubRequest object with the new club data
     * @return ClubResponse with the updated club data
     */
    ClubResponse updateClub(String id, ClubRequest request);

    /**
     * Delete an existing club identified by its ID.
     *
     * @param id Unique identifier of the club to delete
     */
    void deleteClub(String id);

    /**
     * Get data for a specific club by its ID.
     *
     * @param id Unique identifier of the club to query
     * @return Optional containing the ClubResponse if the club is found, or empty if it doesn't exist
     */
    Optional<ClubResponse> getClubById(String id);

    /**
     * Get a paginated list of clubs.
     *
     * @param page Page number to retrieve (starting from 0)
     * @param size Number of clubs per page
     * @return List of ClubResponse clubs on the requested page
     */
    List<ClubResponse> getClubesPaginated(int page, int size);

    /**
     * Get the total number of pages for clubs.
     *
     * @param size Number of clubs per page
     * @return Total number of pages for clubs
     */
    int getTotalPagesClubes(int size);

    /**
     * Get a paginated list of clubs with additional data for each club, such as current page and total pages.
     *
     * @param page Page number to retrieve (starting from 0)
     * @param size Number of clubs per page
     */
    DataPaginator<List<ClubResponse>> getClubsDataPaginator(int page, int size);

    
    /*
     * Add players to a club.
     * @param id Unique identifier of the club
     * @param playerIds List of player IDs to add to the club
     * @return ClubResponse with the updated list of players in the club
     */
    void addPlayersToClub(String id, Set<String> playerIds);

    /**
     * Remove players from a club.
     *
     * @param id Unique identifier of the club
     * @param playerIds List of player IDs to remove from the club
     */
    void removePlayersFromClub(String id, Set<String> playerIds);
}
