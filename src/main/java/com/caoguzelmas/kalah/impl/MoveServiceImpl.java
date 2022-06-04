package com.caoguzelmas.kalah.impl;

import com.caoguzelmas.kalah.model.Game;
import com.caoguzelmas.kalah.model.House;
import com.caoguzelmas.kalah.model.Player;
import com.caoguzelmas.kalah.repository.GameRepository;
import com.caoguzelmas.kalah.service.IMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoveServiceImpl implements IMoveService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game move(int gameId, int selectedHouseIndex) {
        // TODO Exceptions should be under control
        Game activeGame = gameRepository.findGame(gameId);

        replaceStones(activeGame, selectedHouseIndex); // replaceStonesByCounterClockWise
        // TODO replaceStonesByClockWise
        // TODO need to Look over is last stone placed into empty place owned by active player
        // TODO need to look over is game over


        return gameRepository.saveGame(activeGame);
    }

    private void replaceStones(Game activeGame, int houseIndex) {
        Player activePlayer = getActivePlayer(activeGame);
        Player inActivePlayer = getInactivePlayer(activeGame);
        House selectedHouseOfActivePlayer = activePlayer.getHouses().get(houseIndex-1);
        int numberOfStonesOnSelectedHouse = selectedHouseOfActivePlayer.getNumberOfStones();
        selectedHouseOfActivePlayer.setNumberOfStones(0);
        houseIndex++;

        if (activeGame.getFlowsCounterClockwise()) {
            replaceStonesByCounterClockWise(houseIndex, numberOfStonesOnSelectedHouse,
                    activePlayer, inActivePlayer, activeGame);
        } else {
            replaceStonesByClockWise(houseIndex, numberOfStonesOnSelectedHouse,
                    activePlayer, inActivePlayer, activeGame);
        }


    }

    private void replaceStonesByCounterClockWise(int houseIndex, int numberOfStonesOnSelectedHouse,
                                                 Player activePlayer, Player inActivePlayer, Game activeGame) {
        for (int i = numberOfStonesOnSelectedHouse; i > 0; i--) {

            if (houseIndex == activePlayer.getHouses().size() + 1) {
                activePlayer.getPlayerStore().setNumberOfStones(activePlayer.getPlayerStore().getNumberOfStones() + 1);
            } else if (houseIndex > activePlayer.getHouses().size()) {
                int inActivePlayerHouseIndex = houseIndex - (activePlayer.getHouses().size() + 1);
                House currentHouseOfInactivePlayer = inActivePlayer.getHouses().get(inActivePlayerHouseIndex-1);
                currentHouseOfInactivePlayer.setNumberOfStones(currentHouseOfInactivePlayer.getNumberOfStones() + 1);
            } else {
                House currentHouse = activePlayer.getHouses().get(houseIndex-1);
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
            }
            houseIndex++;

            if (houseIndex > (activePlayer.getHouses().size() * 2) + 1) {
                houseIndex = 1;
            }

            if (i == 1) {
                determineActivePlayerForNextTurn(activeGame, houseIndex);

            }
        }
    }

    private void replaceStonesByClockWise(int houseIndex, int numberOfStonesOnSelectedHouse,
                                          Player activePlayer, Player inActivePlayer, Game activeGame) {

    }

    private Player getActivePlayer(Game game) {
        return game.getFirstPlayer().getActivePlayer() ?
                game.getFirstPlayer():
                game.getSecondPlayer();
    }

    private Player getInactivePlayer(Game game) {
        return !game.getFirstPlayer().getActivePlayer() ?
                game.getFirstPlayer():
                game.getSecondPlayer();
    }

    private void determineActivePlayerForNextTurn(Game game, int nextHouseIndex) {
        Player currentActivePlayer = getActivePlayer(game);
        Player currentInactivePlayer = getInactivePlayer(game);
        boolean lastStoneToActivePlayersArea = (nextHouseIndex - currentActivePlayer.getHouses().size()) < 0;
        boolean lastStoneToInActivePlayersArea = (nextHouseIndex - currentActivePlayer.getHouses().size()) > 0;


        if (lastStoneToActivePlayersArea
                && currentActivePlayer.getHouses().get(nextHouseIndex).getNumberOfStones() != 0) {
            currentActivePlayer.setActivePlayer(false);
            currentInactivePlayer.setActivePlayer(true);
        } else if (lastStoneToInActivePlayersArea) {
            currentActivePlayer.setActivePlayer(false);
            currentInactivePlayer.setActivePlayer(true);
        }
    }
}
