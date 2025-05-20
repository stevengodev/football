package com.foliaco.football.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foliaco.football.model.dto.request.ClubRequest;
import com.foliaco.football.model.dto.response.ClubResponse;
import com.foliaco.football.model.dto.response.DataPaginator;
import com.foliaco.football.service.ClubService;
import com.foliaco.football.service.FileStorageService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/v1/clubs")
@AllArgsConstructor
public class ClubController {

    private ClubService clubService;

    private ObjectMapper objectMapper;

    private FileStorageService fileStorageService;

    @Operation(summary = "Get all clubs")
    @GetMapping
    public ResponseEntity<DataPaginator<List<ClubResponse>>> getClubsPaginated(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size){
        
        DataPaginator<List<ClubResponse>> paginator = clubService.getClubsDataPaginator(page, size);
        return new ResponseEntity<>(paginator, HttpStatus.OK);
    }

    @Operation(summary = "Create a new club")
    @PostMapping
    public ResponseEntity<ClubResponse> createClub(@RequestParam(name = "clubJson") String clubRequestJson,
                                                    @RequestParam MultipartFile file){

        ClubRequest clubRequest = null;
        try {
            String fileName = fileStorageService.storeFile(file);
            clubRequest = objectMapper.readValue(clubRequestJson, ClubRequest.class);
            clubRequest.setBadgeUrl(fileName);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        ClubResponse club = clubService.createClub(clubRequest);
        return new ResponseEntity<>(club, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing club")
    @PutMapping("/{id}")
    public ResponseEntity<ClubResponse> updateClub(@PathVariable String id, @RequestParam(name = "clubJson") String clubRequestJson,
                                                    @RequestParam MultipartFile file){

        ClubRequest clubRequest = null;
        Optional<ClubResponse> clubFound = clubService.getClubById(id);

        if(clubFound.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        try {
            String fileName = fileStorageService.storeFile(file);
            clubRequest = objectMapper.readValue(clubRequestJson, ClubRequest.class);
            clubRequest.setBadgeUrl(fileName);

            fileStorageService.deleteFile(clubFound.get().getBadgeUrl());
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        ClubResponse club = clubService.updateClub(id, clubRequest);
        return new ResponseEntity<>(club, HttpStatus.OK);
    }

    @Operation(summary = "Get a club by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable String id){

        Optional<ClubResponse> clubResponse = clubService.getClubById(id);

        if(clubResponse.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        clubService.deleteClub(id);
        fileStorageService.deleteFile(clubResponse.get().getBadgeUrl());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get a list of players in a club")
    @PostMapping("/{id}/players")
    public ResponseEntity<Void> addPlayersToClub(@PathVariable String id, @RequestBody Set<String> playersIds){
        clubService.addPlayersToClub(id, playersIds);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
