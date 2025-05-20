package com.foliaco.football.dao.extended;

import java.util.List;

import com.foliaco.football.model.document.Match;
import com.foliaco.football.model.dto.request.SearchCriteria;

public interface MatchRepositoryExt {

    List<Match> getMatchesByFilters(List<SearchCriteria> searchCriteria);

    List<Match> getMatchesPaginated(int page, int size);

    int getTotalPagesMatches(int size);

}
