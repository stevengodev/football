package com.foliaco.football.dao.repository;

import com.foliaco.football.dao.extended.PlayerRepositoryExt;
import com.foliaco.football.model.document.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, String>, PlayerRepositoryExt {
}
