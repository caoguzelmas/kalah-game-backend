package com.caoguzelmas.kalah.service;

import com.caoguzelmas.kalah.dto.GameRequestDto;
import com.caoguzelmas.kalah.model.Game;

import java.util.List;

public interface IGameService {

    Game createGame(GameRequestDto gameRequestDto);

    List<Game> getAllGames();
}
