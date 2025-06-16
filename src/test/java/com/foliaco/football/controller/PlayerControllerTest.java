package com.foliaco.football.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foliaco.football.model.dto.request.PlayerRequest;
import com.foliaco.football.model.dto.request.SearchCriteria;
import com.foliaco.football.model.dto.response.PlayerResponse;
import com.foliaco.football.service.FileStorageService;
import com.foliaco.football.service.PlayerService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @MockitoBean
    private PlayerService playerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private FileStorageService fileStorageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenId_whenGetPlayerById_shouldReturnPlayer() throws Exception {

        String id = "jugador1";
        PlayerResponse player = PlayerResponse.builder()
                .name("Leonel Messi")
                .bib(10)
                .build();

        when(playerService.getPlayerById(id)).thenReturn(Optional.of(player));

        mockMvc.perform(get("/api/v1/players/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Leonel Messi"));

    }

    @Test
    void givenId_whenDeletePlayer_shouldReturnNoContent() throws Exception {
        String playerId = "1";
        PlayerResponse player = PlayerResponse.builder()
                .name("Leonel Messi")
                .bib(10)
                .build();

        when(playerService.getPlayerById(playerId)).thenReturn(Optional.of(player));

        mockMvc.perform(delete("/api/v1/players/{id}", playerId))
                .andExpect(status().isNoContent());

        verify(playerService).deletePlayer(playerId);
    }

    @Test
    void givenSearchCriteria_whenGetPlayersByFilter_shouldReturnListOfPlayersByCriteria() throws Exception {
        SearchCriteria criteria = new SearchCriteria("bib", "10", "=");

        PlayerResponse player1 = PlayerResponse.builder()
                .name("Leonel Messi")
                .bib(10)
                .build();

        PlayerResponse player2 = PlayerResponse.builder()
                .name("Cristiano Ronaldo")
                .bib(7)
                .build();

        when(playerService.getPlayersByFilter( List.of(criteria) )).thenReturn(List.of(player1));

        mockMvc.perform(post("/api/v1/players/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(criteria))))
                .andExpect(status().isOk());
    }

}
