package com.foliaco.football.dao.extended.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.foliaco.football.dao.extended.MatchRepositoryExt;
import com.foliaco.football.model.document.Match;
import com.foliaco.football.model.dto.request.SearchCriteria;

@Repository
public class MatchRepositoryExtImpl implements MatchRepositoryExt {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MatchRepositoryExtImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Match> getMatchesByFilters(List<SearchCriteria> searchCriteria) {

        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();
        
        if (searchCriteria != null && !searchCriteria.isEmpty()) {


            for (SearchCriteria sc : searchCriteria) {

                String field = sc.getFilterField();
                Object value = sc.getValue();
                String operation = sc.getOperation();

                switch (operation) {
                    case "season":
                        criteriaList.add(Criteria.where(field).is(value));
                        break;
                    case "competition":
                        criteriaList.add(Criteria.where(field).is(value));
                        break;
                    case "localTeam":
                        criteriaList.add(Criteria.where(field).is(value));
                        break;
                    case "visitingTeam":
                        criteriaList.add(Criteria.where(field).is(value));
                        break;
                    case "startDate":
                        criteriaList.add(Criteria.where(field).gte(value));
                        break;
                    case "endDate":
                        criteriaList.add(Criteria.where(field).lte(value));
                        break;
                    case "location":
                        criteriaList.add(Criteria.where(field).is(value));
                        break;
                    case "stadium":
                        criteriaList.add(Criteria.where(field).is(value));
                        break;
                    default:
                        throw new IllegalArgumentException("Operación no válida: " + operation);
                }

            }

            if (!criteriaList.isEmpty()){
                query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
            }

        }

        return mongoTemplate.find(query, Match.class);

    }

    @Override
    public List<Match> getMatchesPaginated(int page, int size) {
       
        Query query = new Query();
        query.skip(size * page).limit(size);
        return mongoTemplate.find(query, Match.class);

    }

    @Override
    public int getTotalPagesMatches(int size) {
       
        long totalMatches = mongoTemplate.count(new Query(), Match.class);
        int totalPages = (int) Math.ceil((double) totalMatches / size);
        return totalPages;
        
    }

    

}
