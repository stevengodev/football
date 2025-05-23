package com.foliaco.football.dao.extended;

import java.util.List;


import com.foliaco.football.model.document.Stat;

public interface StatRepositoryExt {

    List<Stat> geStatsPaginated(int page, int size);
    int getTotalPagesStats(int size);

    

}
