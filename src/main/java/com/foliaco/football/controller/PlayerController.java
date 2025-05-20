package com.foliaco.football.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foliaco.football.model.dto.request.PlayerRequest;
import com.foliaco.football.model.dto.request.SearchCriteria;
import com.foliaco.football.model.dto.response.DataPaginator;
import com.foliaco.football.model.dto.response.PlayerResponse;
import com.foliaco.football.service.FileStorageService;
import com.foliaco.football.service.PlayerService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/players")
@AllArgsConstructor
public class PlayerController {

    private PlayerService playerService;

    private ObjectMapper objectMapper;

    private FileStorageService fileStorageService;

    @Operation(summary = "Create a new player")        
    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer(@RequestParam(name = "playerJson") String playerRequestJson,
                                                    @RequestParam MultipartFile file) {

        PlayerRequest playerRequest = null;
        
        try {
            String fileName = fileStorageService.storeFile(file);
            playerRequest = objectMapper.readValue(playerRequestJson, PlayerRequest.class);
            playerRequest.setPhotoUrl(fileName);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        PlayerResponse playerResponse = playerService.createPlayer(playerRequest);
        return new ResponseEntity<>( playerResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing player")
    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponse> updatePlayer(@PathVariable String id, 
                                                        @RequestParam(name = "playerJson") String playerRequestJson,
                                                        @RequestParam MultipartFile file) {
        PlayerRequest playerRequest = null;
        Optional<PlayerResponse> playerResponse = playerService.getPlayerById(id);

        if(playerResponse.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        try {
            String fileName = fileStorageService.storeFile(file);
            playerRequest = objectMapper.readValue(playerRequestJson, PlayerRequest.class);
            playerRequest.setPhotoUrl(fileName);
            fileStorageService.deleteFile(playerResponse.get().getPhotoUrl());
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(playerService.updatePlayer(id, playerRequest), HttpStatus.OK);
    }

    @Operation(summary = "Get a player by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable String id){
        Optional<PlayerResponse> playerResponse = playerService.getPlayerById(id);

        if(playerResponse.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(playerResponse.get(), HttpStatus.OK);
    }

    @Operation(summary = "Delete a player by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable String id){
        Optional<PlayerResponse> playerResponse = playerService.getPlayerById(id);

        if(playerResponse.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        playerService.deletePlayer(id);
        fileStorageService.deleteFile(playerResponse.get().getPhotoUrl());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get players by filter")
    @PostMapping("/search")
    public List<PlayerResponse> getPlayersByFilter(@RequestBody List<SearchCriteria> searchesCriteria){
        return playerService.getPlayersByFilter(searchesCriteria);
    }

    @Operation(summary = "Get players paginated")
    @GetMapping
    public ResponseEntity<DataPaginator<List<PlayerResponse>>> getPlayerPaginated(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size){

        DataPaginator<List<PlayerResponse>> playersDataPaginator = playerService.getPlayersDataPaginator(page, size);
        return new ResponseEntity<>(playersDataPaginator, HttpStatus.OK);
    }



}
