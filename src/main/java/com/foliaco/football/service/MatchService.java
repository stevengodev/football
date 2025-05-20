package com.foliaco.football.service;

import com.foliaco.football.model.dto.request.MatchRequest;
import com.foliaco.football.model.dto.request.SearchCriteria;
import com.foliaco.football.model.dto.response.DataPaginator;
import com.foliaco.football.model.dto.response.MatchResponse;

import java.util.List;
import java.util.Optional;

public interface MatchService {

    MatchResponse createMatch(MatchRequest request);

    MatchResponse updateMatch(String id, MatchRequest request);

    void deleteMatch(String id);

    Optional<MatchResponse> getMatchById(String id);

    /**
     *
     * @param page number of page
     * @param size number of matches per page
     * @return list of matches
     */
    List<MatchResponse> getMatchesPaginated(int page, int size);

    /**
     *
     * @param page number of page
     * @param size number of matches per page
     * @return DataPaginator with the total number of pages and the list of matches
     */
    DataPaginator<List<MatchResponse>> getMatchesDataPaginator(int page, int size);

    List<MatchResponse> getMatchesByFilter(List<SearchCriteria> searchesCriteria);

}
