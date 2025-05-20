package com.foliaco.football.dao.extended.impl;

import com.foliaco.football.dao.extended.PlayerRepositoryExt;
import com.foliaco.football.model.document.Player;
import com.foliaco.football.model.dto.request.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class PlayerRepositoryExtImpl implements PlayerRepositoryExt {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public PlayerRepositoryExtImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Player> getPlayersByFilters(List<SearchCriteria> searchesCriteria) {
        Query query = new Query();

        if (searchesCriteria!= null && !searchesCriteria.isEmpty()){

            List<Criteria> criteriaList  = new ArrayList<>();

            for (SearchCriteria sc : searchesCriteria) {

                String field = sc.getFilterField();
                Object value = sc.getValue();
                String operation = sc.getOperation();

                switch (operation) {
                    case "=":
                        criteriaList.add(Criteria.where(field).is(value));
                        break;
                    case ">":
                        criteriaList.add(Criteria.where(field).gt(value));
                        break;
                    case ">=":
                        criteriaList.add(Criteria.where(field).gte(value));
                        break;
                    case "<":
                        criteriaList.add(Criteria.where(field).lt(value));
                        break;
                    case "<=":
                        criteriaList.add(Criteria.where(field).lte(value));
                        break;
                    case "!=":
                        criteriaList.add(Criteria.where(field).ne(value));
                        break;
                    case "like":
                        criteriaList.add(Criteria.where(field).regex(".*" + value + ".*", "i"));
                        break;
                    default:
                        throw new IllegalArgumentException("Operación no válida: " + operation);
                }

            }

            if (!criteriaList.isEmpty()){
                query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
            }

        }

        return mongoTemplate.find(query, Player.class);

    }

    @Override
    public List<Player> getPlayersPaginated(int page, int size) {

        Query query = new Query();
        query.skip((long) page * size ).limit( size );
        return mongoTemplate.find(query, Player.class);
    }

    @Override
    public int getTotalPagesPlayers(int size) {

        long totalElements = mongoTemplate.count(new Query(), Player.class);
        int totalPages = (int) Math.ceil( (double) totalElements / size );
        return totalPages;
    }
}
