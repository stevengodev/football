package com.foliaco.football.dao.extended;


import com.foliaco.football.model.document.Player;
import com.foliaco.football.model.dto.request.SearchCriteria;

import java.util.List;

public interface PlayerRepositoryExt {

    List<Player> getPlayersByFilters(List<SearchCriteria> searchCriteria);

    List<Player> getPlayersPaginated(int page, int size);

    int getTotalPagesPlayers(int size);

}
