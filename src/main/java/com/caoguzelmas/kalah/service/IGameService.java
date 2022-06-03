package com.caoguzelmas.kalah.service;

import com.caoguzelmas.kalah.dto.GameRequestDto;
import com.caoguzelmas.kalah.model.Game;

public interface IGameService {

    Game createGame(GameRequestDto gameRequestDto);
}
