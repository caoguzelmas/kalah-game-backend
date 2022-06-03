package com.caoguzelmas.kalah.repository;

import com.caoguzelmas.kalah.model.Game;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameRepository {

    private final Map<Integer, Game> gameMemory = new HashMap<>();

    public Game saveGame(Game game) {
        gameMemory.put(game.getGameId(), game);
        return findGame(game.getGameId());
    }

    public Game findGame(Integer gameId) {
        return gameMemory.get(gameId);
    }


}
