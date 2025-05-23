package com.foliaco.football.dao.extended.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.foliaco.football.dao.extended.StatRepositoryExt;
import com.foliaco.football.model.document.Match;
import com.foliaco.football.model.document.Stat;
import com.foliaco.football.model.dto.request.StatRequest;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class StatRepositoryExtImpl implements StatRepositoryExt {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Stat> geStatsPaginated(int page, int size) {

        Query query = new Query();
        query.skip(size * page);
        query.limit(size);
        return mongoTemplate.find(query, Stat.class);

    }

    @Override
    public int getTotalPagesStats(int size) {
        Query query = new Query();
        long totalElements = mongoTemplate.count(query, Stat.class);
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return totalPages;
    }


}
