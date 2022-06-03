package com.caoguzelmas.kalah.service;

import com.caoguzelmas.kalah.model.Game;

public interface IMoveService {

    Game move(Game currentGameSituation, int selectedHouseIndex);
}
