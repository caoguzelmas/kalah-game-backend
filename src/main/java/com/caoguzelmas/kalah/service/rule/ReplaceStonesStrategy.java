package com.caoguzelmas.kalah.service.rule;

import com.caoguzelmas.kalah.model.Game;

public interface ReplaceStonesStrategy {

    int replaceStones(Game activeGame, int houseIndex);
}
