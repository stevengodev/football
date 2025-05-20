package com.foliaco.football.dao.repository;

import com.foliaco.football.dao.extended.MatchRepositoryExt;
import com.foliaco.football.model.document.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match, String>, MatchRepositoryExt {
}
