package com.foliaco.football.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.foliaco.football.dao.repository.StatRepository;
import com.foliaco.football.exception.NotFoundException;
import com.foliaco.football.mapper.StatMapper;
import com.foliaco.football.model.document.Stat;
import com.foliaco.football.model.dto.request.StatRequest;
import com.foliaco.football.model.dto.response.StatResponse;
import com.foliaco.football.service.StatService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;
    private final StatMapper statMapper;

    @Override
    public StatResponse updateStat(String id, StatRequest statRequest) {

        Stat stat = statRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Stat not found with id: " + id));

        stat = statMapper.toStat(statRequest);
        stat.setId(id);

        Stat updatedStat = statRepository.save(stat);
        return statMapper.toStatResponse(updatedStat);
    }

    @Override
    public void deleteStat(String id) {

        Stat stat = statRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Stat not found with id: " + id));

        statRepository.delete(stat);

    }

    @Override
    public StatResponse getStatById(String id) {

        Stat stat = statRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Stat not found with id: " + id));

        return statMapper.toStatResponse(stat);

    }

    @Override
    public List<StatResponse> getAllStatsByIds(List<String> ids) {

        List<Stat> stats = statRepository.findAllById(ids);

        return stats.stream()
                .map(statMapper::toStatResponse)
                .collect(Collectors.toList());
    }

}
