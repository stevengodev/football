package com.foliaco.football.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foliaco.football.model.dto.request.MatchRequest;
import com.foliaco.football.model.dto.request.SearchCriteria;
import com.foliaco.football.model.dto.response.DataPaginator;
import com.foliaco.football.model.dto.response.MatchResponse;
import com.foliaco.football.service.MatchService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/matches")
@AllArgsConstructor
public class MathController {
    
    private final MatchService matchService;


    @PostMapping
    @Operation(summary = "Create a new match")
    public ResponseEntity<MatchResponse> createMatch(@RequestBody MatchRequest request) {
        MatchResponse matchResponse = matchService.createMatch(request);
        return new ResponseEntity<>(matchResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing match")
    public ResponseEntity<MatchResponse> updateMatch(@PathVariable String id, @RequestBody MatchRequest request) {
        MatchResponse matchResponse = matchService.updateMatch(id, request);
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a match by ID")
    public ResponseEntity<MatchResponse> getMatchById(@PathVariable String id) {
        Optional<MatchResponse> matchResponse = matchService.getMatchById(id);

        if (matchResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(matchResponse.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a match by ID")
    public ResponseEntity<Void> deleteMatch(@PathVariable String id) {
        matchService.deleteMatch(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/search")
    @Operation(summary = "Search for matches by filter")
    public ResponseEntity<List<MatchResponse>> searchMatches(@RequestBody List<SearchCriteria> searchesCriteria) {
        List<MatchResponse> matches = matchService.getMatchesByFilter(searchesCriteria);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get matches paginated")
    public ResponseEntity<DataPaginator<List<MatchResponse>>> getMatchesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        DataPaginator<List<MatchResponse>> matchResponse = matchService.getMatchesDataPaginator(page, size);
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
    }


}
