package com.caoguzelmas.kalah.service;

import com.caoguzelmas.kalah.model.dto.GameRequestDto;
import com.caoguzelmas.kalah.model.Game;

import java.util.List;

public interface IGameService {

    Game createGame(GameRequestDto gameRequestDto);
    Game getGame(String gameId);
    List<Game> getAllGames();
}
