package com.foliaco.football.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.foliaco.football.model.document.Player;
import com.foliaco.football.model.dto.request.PlayerRequest;
import com.foliaco.football.model.dto.request.SearchCriteria;
import com.foliaco.football.model.dto.response.PlayerResponse;
import com.foliaco.football.dao.repository.*;
import com.foliaco.football.exception.NotFoundException;
import com.foliaco.football.service.impl.PlayerServiceImpl;
import com.foliaco.football.mapper.PlayerMapper;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    public void givenPlayerRequest_whenCreatePlayer_thenReturnsPlayerResponse() {

        // Given
        PlayerRequest request = new PlayerRequest();
        request.setName("Lionel Messi");
        request.setBib(10);
        request.setBirthDate(LocalDate.of(1987, 6, 24));
        request.setPhotoUrl("messi.jpg");
        request.setGoals(700);
        request.setAssists(300);
        request.setTrophies(35);
        request.setPosition("Delantero");
        request.setNationality("Argentina");

        Player player = new Player();
        player.setName(request.getName());
        player.setBib(request.getBib());
        player.setBirthDate(request.getBirthDate());
        player.setPhotoUrl(request.getPhotoUrl());
        player.setGoals(request.getGoals());
        player.setAssists(request.getAssists());
        player.setTrophies(request.getTrophies());
        player.setPosition(request.getPosition());
        player.setNationality(request.getNationality());

        PlayerResponse expectedResponse = PlayerResponse.builder()
                .name(player.getName())
                .bib(player.getBib())
                .birthDate(player.getBirthDate())
                .photoUrl(player.getPhotoUrl())
                .goals(player.getGoals())
                .assists(player.getAssists())
                .trophies(player.getTrophies())
                .position(player.getPosition())
                .nationality(player.getNationality())
                .build();

        // When
        when(playerRepository.save(any(Player.class))).thenReturn(player);
        when(playerMapper.toPlayer(any(PlayerRequest.class))).thenReturn(player);
        when(playerMapper.toPlayerResponse(any(Player.class))).thenReturn(expectedResponse);

        PlayerResponse response = playerService.createPlayer(request);

        // Then
        assertNotNull(response);
        assertEquals(expectedResponse.getName(), response.getName());
        assertEquals(expectedResponse.getBib(), response.getBib());

    }

    @Test

    public void givenValidIdAndRequest_whenUpdatePlayer_thenReturnsUpdatedPlayerResponse() {

        // Given
        String id = "123";

        Player existingPlayer = new Player();
        existingPlayer.setId(id);
        existingPlayer.setName("Old Name");

        PlayerRequest request = new PlayerRequest();
        request.setName("Lionel Messi");
        request.setBib(10);
        request.setBirthDate(LocalDate.of(1987, 6, 24));
        request.setPhotoUrl("messi.jpg");
        request.setGoals(700);
        request.setAssists(300);
        request.setTrophies(35);
        request.setPosition("Delantero");
        request.setNationality("Argentina");

        Player updatedPlayer = new Player();
        updatedPlayer.setId(id);
        updatedPlayer.setName(request.getName());
        updatedPlayer.setBib(request.getBib());
        updatedPlayer.setBirthDate(request.getBirthDate());
        updatedPlayer.setPhotoUrl(request.getPhotoUrl());
        updatedPlayer.setGoals(request.getGoals());
        updatedPlayer.setAssists(request.getAssists());
        updatedPlayer.setTrophies(request.getTrophies());
        updatedPlayer.setPosition(request.getPosition());
        updatedPlayer.setNationality(request.getNationality());

        PlayerResponse expectedResponse = PlayerResponse.builder()
                .name(updatedPlayer.getName())
                .bib(updatedPlayer.getBib())
                .birthDate(updatedPlayer.getBirthDate())
                .photoUrl(updatedPlayer.getPhotoUrl())
                .goals(updatedPlayer.getGoals())
                .assists(updatedPlayer.getAssists())
                .trophies(updatedPlayer.getTrophies())
                .position(updatedPlayer.getPosition())
                .nationality(updatedPlayer.getNationality())
                .build();

        // When
        when(playerRepository.findById(id)).thenReturn(Optional.of(existingPlayer));
        when(playerRepository.save(existingPlayer)).thenReturn(updatedPlayer);
        when(playerMapper.toPlayerResponse(updatedPlayer)).thenReturn(expectedResponse);

        PlayerResponse response = playerService.updatePlayer(id, request);

        // Then
        assertNotNull(response);
        assertEquals(expectedResponse.getName(), response.getName());
        assertEquals(expectedResponse.getPosition(), response.getPosition());
        verify(playerRepository).findById(id);
        verify(playerRepository).save(existingPlayer);
        verify(playerMapper).toPlayerResponse(updatedPlayer);
    }

    @Test
    public void givenInvalidId_whenUpdatePlayer_thenThrowsNotFoundException() {
        // Given
        String id = "id-not-found";
        PlayerRequest request = new PlayerRequest();

        // When
        when(playerRepository.findById(id)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            playerService.updatePlayer(id, request);
        });

        // Then
        assertEquals("Player not found with id " + id, exception.getMessage());
        assertNotNull(exception.getMessage());
        verify(playerRepository).findById(id);
    }

    @Test
    public void givenExistingPlayerId_whenDeletePlayer_thenDeletesPlayer() {
        // Given
        String id = "player123";
        Player player = new Player();
        player.setId(id);

        // When
        when(playerRepository.findById(id)).thenReturn(Optional.of(player));
        playerService.deletePlayer(id);

        // Then
        verify(playerRepository).findById(id);
        verify(playerRepository).deleteById(id);

    }

    @Test
    public void givenNonExistingPlayerId_whenDeletePlayer_thenThrowsNotFoundException() {
        // Given
        String id = "id-not-found";

        // When
        when(playerRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            playerService.deletePlayer(id);
        });

        // Then
        assertNotNull(exception.getMessage());
        assertEquals("Player not found with id " + id, exception.getMessage());
        verify(playerRepository).findById(id);
        verify(playerRepository, never()).deleteById(any());
    }

    @Test
    public void givenExistingPlayerId_whenGetPlayerById_thenReturnsPlayerResponse() {

        // Given
        String id = "player123";
        Player player = new Player();
        player.setId(id);
        player.setName("Lionel Messi");
        player.setBib(10);
        player.setBirthDate(LocalDate.of(1987, 6, 24));
        player.setPhotoUrl("messi.jpg");
        player.setGoals(700);
        player.setAssists(300);
        player.setTrophies(35);
        player.setPosition("Delantero");
        player.setNationality("Argentina");

        PlayerResponse expectedResponse = PlayerResponse.builder()
                .name(player.getName())
                .bib(player.getBib())
                .birthDate(player.getBirthDate())
                .photoUrl(player.getPhotoUrl())
                .goals(player.getGoals())
                .assists(player.getAssists())
                .trophies(player.getTrophies())
                .position(player.getPosition())
                .nationality(player.getNationality())
                .build();

        // When
        when(playerRepository.findById(id)).thenReturn(Optional.of(player));
        when(playerMapper.toPlayerResponse(player)).thenReturn(expectedResponse);
        Optional<PlayerResponse> playerResponse = playerService.getPlayerById(id);

        // Then
        assertTrue(playerResponse.isPresent());
        assertEquals(expectedResponse.getName(), playerResponse.get().getName());

    }

    @Test
    public void givenNonExistingPlayerId_whenGetPlayerById_thenReturnsEmptyOptional() {
        // Given
        String id = "id-not-found";

        // When
        when(playerRepository.findById(id)).thenReturn(Optional.empty());
        Optional<PlayerResponse> result = playerService.getPlayerById(id);

        // Then
        assertFalse(result.isPresent());
        verify(playerRepository).findById(id);
    }

    @Test
    void givenPageAndSize_whenGetPlayersPaginated_thenReturnListOfPlayerResponse() {

        // Given
        int page = 0;
        int size = 10;

        Player player1 = new Player();
        player1.setName("Lionel Messi");
        player1.setBib(10);
        player1.setBirthDate(LocalDate.of(1987, 6, 24));
        player1.setPhotoUrl("messi.jpg");
        player1.setGoals(700);
        player1.setAssists(300);
        player1.setTrophies(35);
        player1.setPosition("Delantero");
        player1.setNationality("Argentina");

        Player player2 = new Player();
        player2.setName("Cristiano Ronaldo");
        player2.setBib(7);
        player2.setBirthDate(LocalDate.of(1985, 2, 5));
        player2.setPhotoUrl("ronaldo.jpg");
        player2.setGoals(800);
        player2.setAssists(250);
        player2.setTrophies(30);
        player2.setPosition("Delantero");
        player2.setNationality("Portugal");

        PlayerResponse playerResponse1 = PlayerResponse.builder()
                .name(player1.getName())
                .bib(player1.getBib())
                .birthDate(player1.getBirthDate())
                .photoUrl(player1.getPhotoUrl())
                .goals(player1.getGoals())
                .assists(player1.getAssists())
                .trophies(player1.getTrophies())
                .position(player1.getPosition())
                .build();

        PlayerResponse playerResponse2 = PlayerResponse.builder()
                .name(player2.getName())
                .bib(player2.getBib())
                .birthDate(player2.getBirthDate())
                .photoUrl(player2.getPhotoUrl())
                .goals(player2.getGoals())
                .assists(player2.getAssists())
                .trophies(player2.getTrophies())
                .position(player2.getPosition())
                .build();

        List<Player> players = List.of(player1, player2);
        List<PlayerResponse> playersExpected = List.of(playerResponse1, playerResponse2);

        // When
        when(playerRepository.getPlayersPaginated(page, size)).thenReturn(players);
        when(playerMapper.toPlayersResponse(players)).thenReturn(playersExpected);
        List<PlayerResponse> playerResponses = playerService.getPlayersPaginated(page, size);

        // Then
        assertEquals(playersExpected, playerResponses);
        assertEquals(playersExpected.size(), playerResponses.size());
        assertEquals(playersExpected.get(0).getName(), playerResponses.get(0).getName());
        assertEquals(playersExpected.get(1).getName(), playerResponses.get(1).getName());
        verify(playerRepository).getPlayersPaginated(page, size);

    }

    @Test
    void givenSize_whenGetTotalPagesPlayers_thenReturnTotalPages() {
        // Given
        int size = 5;
        int expectedPages = 1;

        // When
        when(playerRepository.getTotalPagesPlayers(size)).thenReturn(expectedPages);
        int result = playerService.getTotalPagesPlayers(size);

        // Then
        assertEquals(expectedPages, result);
        verify(playerRepository).getTotalPagesPlayers(size);
    }

    @Test
    void givenSearchCriteriaList_whenGetPlayersByFilter_thenReturnFilteredPlayers() {
        // Given
        
        Player player = new Player();
        player.setName("Lionel Messi");
        player.setBib(10);
        player.setBirthDate(LocalDate.of(1987, 6, 24));
        player.setPhotoUrl("messi.jpg");
        player.setGoals(700);
        player.setAssists(300);
        player.setTrophies(35);
        player.setPosition("Delantero");
        player.setNationality("Argentina");

        PlayerResponse playerResponse = PlayerResponse.builder()
                .name(player.getName())
                .bib(player.getBib())
                .birthDate(player.getBirthDate())
                .photoUrl(player.getPhotoUrl())
                .goals(player.getGoals())
                .assists(player.getAssists())
                .trophies(player.getTrophies())
                .position(player.getPosition())
                .build();

        List<SearchCriteria> filters = List.of(new SearchCriteria("bib", "=", "10"));
        List<Player> players = List.of(player);
        List<PlayerResponse> responses = List.of(playerResponse);

        // When
        when(playerRepository.getPlayersByFilters(filters)).thenReturn(players);
        when(playerMapper.toPlayersResponse(players)).thenReturn(responses);
        List<PlayerResponse> result = playerService.getPlayersByFilter(filters);

        // Then
        assertEquals(1, result.size());
        verify(playerRepository).getPlayersByFilters(filters);
        verify(playerMapper).toPlayersResponse(players);
    }

}
