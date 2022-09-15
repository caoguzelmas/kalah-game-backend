package com.caoguzelmas.kalah.service.strategy.impl;

import com.caoguzelmas.kalah.model.Game;
import com.caoguzelmas.kalah.model.House;
import com.caoguzelmas.kalah.service.strategy.ReplaceStonesStrategy;

public class ReplaceStonesByClockwise implements ReplaceStonesStrategy {

    @Override
    public int replaceStones(Game activeGame, int houseIndex) {
        House currentHouse = activeGame.getGameBoard().getHouses().get(houseIndex);
        int numberOfStonesOnSelectedHouse = activeGame.getGameBoard().getHouses().get(houseIndex).getNumberOfStones();
        currentHouse.setNumberOfStones(0);
        houseIndex -=1;

        while(numberOfStonesOnSelectedHouse > 0) {

            if (houseIndex == activeGame.getStoreIndexOfInactivePlayer()) {
                houseIndex -=1;
            } else if (houseIndex < 0) {
                houseIndex = activeGame.getActivePlayer().getPlayerId().equals(1) ?
                        activeGame.getGameBoard().getHouseIdOfLastHouse() - 1 :
                        activeGame.getGameBoard().getHouseIdOfLastHouse();
            }

            currentHouse = activeGame.getGameBoard().getHouses().get(houseIndex);
            currentHouse.addOne();
            houseIndex -=1;
            numberOfStonesOnSelectedHouse--;
        }
        return houseIndex + 1;
    }
}
