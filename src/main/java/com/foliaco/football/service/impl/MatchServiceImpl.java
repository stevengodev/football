package com.foliaco.football.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.foliaco.football.dao.repository.MatchRepository;
import com.foliaco.football.dao.repository.StatRepository;
import com.foliaco.football.exception.InvalidDataException;
import com.foliaco.football.exception.NotFoundException;
import com.foliaco.football.mapper.MatchMapper;
import com.foliaco.football.mapper.StatMapper;
import com.foliaco.football.model.document.Match;
import com.foliaco.football.model.document.Stat;
import com.foliaco.football.model.dto.request.MatchRequest;
import com.foliaco.football.model.dto.request.SearchCriteria;
import com.foliaco.football.model.dto.request.StatRequest;
import com.foliaco.football.model.dto.response.DataPaginator;
import com.foliaco.football.model.dto.response.MatchResponse;
import com.foliaco.football.model.dto.response.StatResponse;
import com.foliaco.football.service.MatchService;
import com.foliaco.football.service.StatService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    private final StatService statService;

    private final MatchMapper matchMapper;

    private final StatRepository statRepository;

    private final StatMapper statMapper;

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
                () -> new NotFoundException("Match not found with id: " + id));

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

        Match match = matchRepository.findById(id).orElseThrow(() -> {
            log.error("Match not found with id: {}", id);
            return new NotFoundException("Match not found with id: " + id);
        });

        matchRepository.delete(match);

    }

    @Override
    public Optional<MatchResponse> getMatchById(String id) {

        Optional<Match> match = matchRepository.findById(id);

        if (match.isEmpty()) {
            throw new NotFoundException("Match not found with id: " + id);
        }

        MatchResponse matchResponse = matchMapper.toMatchResponse(match.get());

        matchResponse.setStatResponse(statService.getStatById(match.get().getStatId()));

        log.info("Match found: {}", matchResponse);
        return Optional.of(matchResponse);

    }

    @Override
    public List<MatchResponse> getMatchesPaginated(int page, int size) {

        if (page < 0 || size < 1) {
            throw new InvalidDataException("Invalid data");
        }

        List<Match> matches = matchRepository.getMatchesPaginated(page, size);

        List<String> statIds = matches.stream()
                .map(Match::getStatId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        List<Stat> stats = statRepository.findAllById(statIds);
        
        Map<String, Stat> statMap = stats.stream()
                .collect(Collectors.toMap(Stat::getId, Function.identity()));

        return matches.stream().map(match -> {
            MatchResponse response = new MatchResponse();
            response.setSeason(match.getSeason());
            response.setCompetition(match.getCompetition());
            response.setLocalTeamId(match.getLocalTeamId());
            response.setVisitingTeamId(match.getVisitingTeamId());
            response.setDateTime(match.getDateTime());
            response.setLocation(match.getLocation());
            response.setStadium(match.getStadium());

            Stat stat = statMap.get(match.getStatId());
            if (stat != null) {
                response.setStatResponse(statMapper.toStatResponse(stat));
            }

            return response;
        }).collect(Collectors.toList());

        // List<Match> matches = matchRepository.getMatchesPaginated(page, size);
        // MatchResponse matchResponse = null;
        // List<MatchResponse> matchResponses = new ArrayList<>();

        // for (Match match : matches) {
        // matchResponse = new MatchResponse();
        // StatResponse stat = statService.getStatById(match.getStatId());

        // matchResponse.setSeason(match.getSeason());
        // matchResponse.setCompetition(match.getCompetition());
        // matchResponse.setLocalTeamId(match.getLocalTeamId());
        // matchResponse.setVisitingTeamId(match.getVisitingTeamId());
        // matchResponse.setDateTime(match.getDateTime());
        // matchResponse.setLocation(match.getLocation());
        // matchResponse.setStadium(match.getStadium());
        // matchResponse.setStatResponse(stat);
        // matchResponses.add(matchResponse);
        // }

        // return matchResponses;

    }

    @Override
    public DataPaginator<List<MatchResponse>> getMatchesDataPaginator(int page, int size) {

        List<MatchResponse> matches = getMatchesPaginated(page, size);
        int totalPages = matchRepository.getTotalPagesMatches(size);
        return new DataPaginator<>(matches, totalPages, page);

    }

    @Override
    public List<MatchResponse> getMatchesByFilter(List<SearchCriteria> searchesCriteria) {

        if (searchesCriteria.isEmpty()) {
            throw new InvalidDataException("Invalid data");
        }

        List<Match> matches = matchRepository.getMatchesByFilters(searchesCriteria);

        List<String> statIds = matches.stream()
                .map(Match::getStatId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        List<Stat> stats = statRepository.findAllById(statIds);

        Map<String, Stat> statMap = stats.stream()
                .collect(Collectors.toMap(Stat::getId, Function.identity()));

        return matches.stream().map(match -> {
            MatchResponse response = new MatchResponse();
            response.setSeason(match.getSeason());
            response.setCompetition(match.getCompetition());
            response.setLocalTeamId(match.getLocalTeamId());
            response.setVisitingTeamId(match.getVisitingTeamId());
            response.setDateTime(match.getDateTime());
            response.setLocation(match.getLocation());
            response.setStadium(match.getStadium());

            Stat stat = statMap.get(match.getStatId());
            if (stat != null) {
                response.setStatResponse(statMapper.toStatResponse(stat));
            }

            return response;
        }).collect(Collectors.toList());


    }

}
