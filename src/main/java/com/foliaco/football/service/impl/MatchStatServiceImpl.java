package com.foliaco.football.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.foliaco.football.dao.repository.MatchRepository;
import com.foliaco.football.dao.repository.StatRepository;
import com.foliaco.football.exception.NotFoundException;
import com.foliaco.football.mapper.MatchMapper;
import com.foliaco.football.mapper.StatMapper;
import com.foliaco.football.model.document.Match;
import com.foliaco.football.model.document.Stat;
import com.foliaco.football.model.dto.request.StatRequest;
import com.foliaco.football.model.dto.response.MatchResponse;
import com.foliaco.football.service.MatchStatService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MatchStatServiceImpl implements MatchStatService {

    private final MatchRepository matchRepository;

    private final MatchMapper matchMapper;

    private final StatRepository statRepository;

    private final StatMapper statMapper;

    private static final Logger log = LoggerFactory.getLogger(MatchServiceImpl.class);

    @Override
    public void addStatToMatch(String matchId, StatRequest statRequest) {

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new NotFoundException("Match not found with id: " + matchId));

        Stat stat = statMapper.toStat(statRequest);
        stat = statRepository.save(stat);

        match.setStatId(stat.getId());
        Match updatedMatch = matchRepository.save(match);

        MatchResponse matchResponse = matchMapper.toMatchResponse(updatedMatch);
        log.info("Stat added to match successfully: {}", matchResponse);

    }

    @Override
    public void updateStatInMatch(String matchId, StatRequest statRequest) {
        
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new NotFoundException("Match not found with id: " + matchId));

        Stat stat = statRepository.findById(match.getStatId())
                .orElseThrow(() -> new NotFoundException("Stat not found with id: " + match.getStatId()));

        stat = statMapper.toStat(statRequest);
        stat.setId(match.getStatId());

        Stat updatedStat = statRepository.save(stat);
        log.info("Stat updated in match successfully: {}", updatedStat);

    }

}
