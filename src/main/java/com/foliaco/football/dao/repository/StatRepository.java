package com.foliaco.football.dao.repository;

import com.foliaco.football.dao.extended.StatRepositoryExt;
import com.foliaco.football.model.document.Stat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatRepository extends MongoRepository<Stat, String>, StatRepositoryExt {
}
