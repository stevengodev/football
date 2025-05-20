package com.foliaco.football.dao.extended;

import com.foliaco.football.model.document.Club;

import java.util.List;
import java.util.Set;

public interface ClubRepositoryExt {

    List<Club> getClubesPaginated(int page, int size);

    int getTotalPagesClubes(int size);

    void removePlayersFromClub(String id, Set<String> playerIds);
        
    void addPlayersToClub(String id, Set<String> playerIds);
}
