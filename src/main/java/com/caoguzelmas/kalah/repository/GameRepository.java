package com.caoguzelmas.kalah.repository;

import com.caoguzelmas.kalah.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
}
