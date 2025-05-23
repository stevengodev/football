package com.foliaco.football.service;


import java.util.List;

import com.foliaco.football.model.dto.request.StatRequest;
import com.foliaco.football.model.dto.response.StatResponse;

public interface StatService {

    StatResponse updateStat(String id, StatRequest statRequest);

    void deleteStat(String id);

    StatResponse getStatById(String id);

    List<StatResponse> getAllStatsByIds(List<String> ids);

}
