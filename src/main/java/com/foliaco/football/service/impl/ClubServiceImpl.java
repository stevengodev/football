package com.foliaco.football.service.impl;

import com.foliaco.football.dao.repository.ClubRepository;
import com.foliaco.football.exception.InvalidDataException;
import com.foliaco.football.exception.NotFoundException;
import com.foliaco.football.mapper.ClubMapper;
import com.foliaco.football.model.document.Club;
import com.foliaco.football.model.dto.request.ClubRequest;
import com.foliaco.football.model.dto.response.ClubResponse;
import com.foliaco.football.model.dto.response.DataPaginator;
import com.foliaco.football.service.ClubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    private final ClubMapper clubMapper;

    private final Logger logger = LoggerFactory.getLogger(ClubServiceImpl.class);

    public ClubServiceImpl(ClubRepository clubRepository, ClubMapper clubMapper) {
        this.clubRepository = clubRepository;
        this.clubMapper = clubMapper;
    }

    @Override
    public ClubResponse createClub(ClubRequest request) {
        Club club = clubRepository.save(clubMapper.toClub(request));
        logger.info("Saved club");
        return clubMapper.toClubResponse(club);
    }

    @Override
    public ClubResponse updateClub(String id, ClubRequest request) {

        Club club = clubRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Club not found with id: " + id)
        );

        club.setName(request.getName());
        club.setCountry(request.getCountry());
        club.setLeague(request.getLeague());
        club.setPresident(request.getPresident());
        club.setAcronym(request.getAcronym());
        club.setFoundationDate(request.getFoundationDate());
        club.setStadiumName(request.getStadiumName());
        club.setCoachName(request.getCoachName());
        club.setStadiumCapacity(request.getStadiumCapacity());

        return clubMapper.toClubResponse(clubRepository.save(club));

    }

    @Override
    public void deleteClub(String id) {

        Club club = clubRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Club not found with id: " + id)
        );

        clubRepository.delete(club);

    }

    @Override
    public Optional<ClubResponse> getClubById(String id) {

        Optional<Club> club = clubRepository.findById(id);

        if (club.isEmpty()){
            throw new NotFoundException("Club not found with id: " + id);
        }

        return club.map(clubMapper::toClubResponse);

    }

    @Override
    public List<ClubResponse> getClubesPaginated(int page, int size) {

        if (page < 0 || size < 1) {
            throw new InvalidDataException("Invalid data");
        }

        List<Club> clubes = clubRepository.getClubesPaginated(page, size);
        return clubMapper.toClubesResponse(clubes);

    }

    @Override
    public int getTotalPagesClubes(int size) {
        return clubRepository.getTotalPagesClubes(size);
    }

    public DataPaginator<List<ClubResponse>> getClubsDataPaginator(int page, int size){
        List<ClubResponse> clubes = getClubesPaginated(page, size);
        int totalPages = getTotalPagesClubes(size);
        return new DataPaginator<>(clubes, totalPages, page);
    }

    @Override
    public void addPlayersToClub(String id, Set<String> playerIds) {
        
        Club club = clubRepository.findById(id).orElse(null);

        if (club == null) {
            throw new NotFoundException("Club not found with id: " + id);
        }

        clubRepository.addPlayersToClub(id, playerIds);
        logger.info("Added players to club with id: " + id);

    }

    @Override
    public void removePlayersFromClub(String id, Set<String> playerIds) {

        Club club = clubRepository.findById(id).orElse(null);

        if (club == null) {
            throw new NotFoundException("Club not found with id: " + id);
        }

        if (playerIds.isEmpty()) {
            throw new InvalidDataException("Player IDs cannot be empty");
        }

        clubRepository.removePlayersFromClub(id, playerIds);
        logger.info("Removed players from club with id: " + id);
    }

}
