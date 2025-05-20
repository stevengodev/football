package com.foliaco.football.dao.repository;

import com.foliaco.football.dao.extended.ClubRepositoryExt;
import com.foliaco.football.model.document.Club;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClubRepository extends MongoRepository<Club, String>, ClubRepositoryExt {
}
