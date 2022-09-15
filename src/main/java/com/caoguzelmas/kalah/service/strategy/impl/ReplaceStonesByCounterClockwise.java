package com.caoguzelmas.kalah.service.strategy.impl;

import com.caoguzelmas.kalah.model.Game;
import com.caoguzelmas.kalah.model.House;
import com.caoguzelmas.kalah.service.strategy.ReplaceStonesStrategy;

public class ReplaceStonesByCounterClockwise implements ReplaceStonesStrategy {

    @Override
    public int replaceStones(Game activeGame, int houseIndex) {
        House currentHouse = activeGame.getGameBoard().getHouses().get(houseIndex);
        int numberOfStonesOnSelectedHouse = activeGame.getGameBoard().getHouses().get(houseIndex).getNumberOfStones();
        currentHouse.setNumberOfStones(0);
        houseIndex +=1;

        while(numberOfStonesOnSelectedHouse > 0) {

            if (houseIndex == activeGame.getStoreIndexOfInactivePlayer()) {
                houseIndex = activeGame.getActivePlayer().getPlayerId().equals(1) ? 0 : +1;
            } else if (houseIndex > activeGame.getGameBoard().getHouseIdOfLastHouse()) {
                houseIndex = 0;
            }

            currentHouse = activeGame.getGameBoard().getHouses().get(houseIndex);
            currentHouse.addOne();
            houseIndex +=1;
            numberOfStonesOnSelectedHouse--;
        }
        return houseIndex - 1;
    }
}
