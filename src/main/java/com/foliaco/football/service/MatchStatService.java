package com.foliaco.football.service;

import com.foliaco.football.model.dto.request.StatRequest;

public interface MatchStatService {
    
    void addStatToMatch(String matchId, StatRequest statRequest);

    void updateStatInMatch(String matchId, StatRequest statRequest);

}
