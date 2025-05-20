package com.foliaco.football.dao.extended.impl;

import com.foliaco.football.dao.extended.ClubRepositoryExt;
import com.foliaco.football.exception.NotFoundException;
import com.foliaco.football.model.document.Club;
import com.mongodb.client.result.UpdateResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ClubRepositoryExtImpl implements ClubRepositoryExt {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ClubRepositoryExtImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Club> getClubesPaginated(int page, int size) {
        Query query = new Query();
        query.skip((long) page * size).limit(size);
        return mongoTemplate.find(query, Club.class);
    }

    public int getTotalPagesClubes(int size){

        long totalElements = mongoTemplate.count(new Query(), Club.class);
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return totalPages;
    }

    @Override
    public void removePlayersFromClub(String id, Set<String> playerIds) {

        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().pullAll("playersId", playerIds.toArray());
        UpdateResult result = mongoTemplate.updateFirst(query, update, Club.class);

        if (result.getMatchedCount() == 0) {
            throw new NotFoundException("Club not found with id: " + id);
        }

    }

    @Override
    public void addPlayersToClub(String id, Set<String> playerIds) {
        
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().addToSet("playersId").each(playerIds.toArray());
        UpdateResult result = mongoTemplate.updateFirst(query, update, Club.class);

        if (result.getMatchedCount() == 0) {
            throw new NotFoundException("Club not found with id: " + id);
        }

    }

}
