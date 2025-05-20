package com.foliaco.football.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.foliaco.football.dao.repository.MatchRepository;
import com.foliaco.football.exception.InvalidDataException;
import com.foliaco.football.exception.NotFoundException;
import com.foliaco.football.mapper.MatchMapper;
import com.foliaco.football.model.document.Match;
import com.foliaco.football.model.dto.request.MatchRequest;
import com.foliaco.football.model.dto.request.SearchCriteria;
import com.foliaco.football.model.dto.response.DataPaginator;
import com.foliaco.football.model.dto.response.MatchResponse;
import com.foliaco.football.service.MatchService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService{

    private final MatchRepository matchRepository;

    private final MatchMapper matchMapper;

    private static final Logger log = LoggerFactory.getLogger(MatchServiceImpl.class);

    @Override
    public MatchResponse createMatch(MatchRequest request) {

        Match match = matchMapper.toMatch(request);
        Match savedMatch = matchRepository.save(match);
        MatchResponse matchResponse = matchMapper.toMatchResponse(savedMatch);

        log.info("Match created successfully: {}", matchResponse);
        return matchResponse;
    }

    @Override
    public MatchResponse updateMatch(String id, MatchRequest request) {
       
        Match match = matchRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Match not found with id: " + id)
        );

        match.setMvp(request.getMvp());
        match.setSeason(request.getSeason());
        match.setCompetition(request.getCompetition());
        match.setLocalTeamId(request.getLocalTeamId());
        match.setVisitingTeamId(request.getVisitingTeamId());
        match.setDateTime(request.getDateTime());
        match.setLocation(request.getLocation());
        match.setStadium(request.getStadium());

        Match updatedMatch = matchRepository.save(match);
        MatchResponse matchResponse = matchMapper.toMatchResponse(updatedMatch);
        log.info("Match updated successfully: {}", matchResponse);
        return matchResponse;
    }

    @Override
    public void deleteMatch(String id) {
        
        Match match = matchRepository.findById(id).orElseThrow( () -> {
            log.error("Match not found with id: {}", id);
            return new NotFoundException("Match not found with id: " + id);
        });

        matchRepository.delete(match);

    }

    @Override
    public Optional<MatchResponse> getMatchById(String id) {
        
        Optional<Match> match = matchRepository.findById(id);

        if (match.isEmpty()){
            throw new NotFoundException("Match not found with id: " + id);
        }

        MatchResponse matchResponse = matchMapper.toMatchResponse(match.get());
        log.info("Match found: {}", matchResponse);
        return Optional.of(matchResponse);

    }

    @Override
    public List<MatchResponse> getMatchesPaginated(int page, int size) {
    
        if (page < 0 || size < 1) {
            throw new InvalidDataException("Invalid data");
        }

        List<Match> matches = matchRepository.getMatchesPaginated(page, size);
        return matchMapper.toMatchesResponse(matches);

    }

    @Override
    public DataPaginator<List<MatchResponse>> getMatchesDataPaginator(int page, int size) {
        
        List<MatchResponse> matches = getMatchesPaginated(page, size);
        int totalPages = matchRepository.getTotalPagesMatches(size);
        return new DataPaginator<>(matches, totalPages, page);

    }

    @Override
    public List<MatchResponse> getMatchesByFilter(List<SearchCriteria> searchesCriteria) {
        
        if (searchesCriteria.isEmpty()){
            throw new InvalidDataException("Invalid data");
        }

        List<Match> matches = matchRepository.getMatchesByFilters(searchesCriteria);
        return matchMapper.toMatchesResponse(matches);

    }

    
}
