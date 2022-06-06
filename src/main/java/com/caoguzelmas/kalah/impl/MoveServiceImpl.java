package com.caoguzelmas.kalah.impl;

import com.caoguzelmas.kalah.exceptions.IllegalMoveException;
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
        Game activeGame = gameRepository.findGame(gameId);
        validateMove(gameId, selectedHouseIndex);
        replaceStones(activeGame, selectedHouseIndex);
        checkIsGameOver(activeGame);

        return gameRepository.saveGame(activeGame);
    }

    private void validateMove(int gameId, int selectedHouseIndex) {
        Game activeGame = gameRepository.findGame(gameId);

        if (selectedHouseIndex < 0 || selectedHouseIndex > activeGame.getFirstPlayer().getHouses().size()) {
            throw new IllegalMoveException("Selected Index does not exist!");
        }

        if (activeGame.getActivePlayer().getHouses().get(selectedHouseIndex).getNumberOfStones() == 0) {
            throw new IllegalMoveException("Selected house is empty!");
        }
    }

    private void replaceStones(Game activeGame, int houseIndex) {
        Player activePlayer = activeGame.getActivePlayer();
        Player inActivePlayer = activeGame.getInactivePlayer();
        House selectedHouseOfActivePlayer = activePlayer.getHouses().get(houseIndex);
        int numberOfStonesOnSelectedHouse = selectedHouseOfActivePlayer.getNumberOfStones();
        selectedHouseOfActivePlayer.setNumberOfStones(0);

        if (activeGame.getFlowsCounterClockwise()) {
            int nextHouseIndex = houseIndex+1;
            replaceStonesByCounterClockWise(nextHouseIndex, numberOfStonesOnSelectedHouse, activePlayer, inActivePlayer, activeGame);
        } else {
            int previousHouseIndex = houseIndex-1;
            replaceStonesByClockWise(previousHouseIndex, numberOfStonesOnSelectedHouse, activePlayer, inActivePlayer, activeGame);
        }
    }

    private void replaceStonesByCounterClockWise(int houseIndex, int numberOfStonesOnSelectedHouse,
                                                 Player activePlayer, Player inActivePlayer, Game activeGame) {
        House currentHouse;

        while(numberOfStonesOnSelectedHouse > 0) {
            // controls active players store
            if (houseIndex == activePlayer.getHouses().size()) {
                activePlayer.getPlayerStore().setNumberOfStones(activePlayer.getPlayerStore().getNumberOfStones() + 1);
                // controls inActive players store
            } else if (houseIndex > (activePlayer.getHouses().size() * 2)) {
                houseIndex = 0;
                currentHouse = activePlayer.getHouses().get(houseIndex);
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
                // controls inActive players houses
            } else if (houseIndex > activePlayer.getHouses().size()) {
                int inActivePlayerHouseIndex = houseIndex - (activePlayer.getHouses().size() + 1);
                currentHouse = inActivePlayer.getHouses().get(inActivePlayerHouseIndex);
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
                // controls active players houses
            } else {
                currentHouse = activePlayer.getHouses().get(houseIndex);
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
            }

            if (numberOfStonesOnSelectedHouse == 1) {
                determineActivePlayerForNextTurn(activeGame, houseIndex);
                // TODO need to Look over is last stone placed into empty place owned by active player
                // lookOverEmptyCapture(activeGame, houseIndex);

            }
            houseIndex++;
            numberOfStonesOnSelectedHouse--;
        }
    }

    private void replaceStonesByClockWise(int houseIndex, int numberOfStonesOnSelectedHouse,
                                          Player activePlayer, Player inActivePlayer, Game activeGame) {
        House currentHouse;

        while (numberOfStonesOnSelectedHouse > 0) {
            // controls inActive players store
            if (houseIndex == -1) {
                houseIndex = -2;
                currentHouse = inActivePlayer.getHouses().get(houseIndex + inActivePlayer.getHouses().size() + 1);
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
                // controls active players store
            } else if (houseIndex == (-(activePlayer.getHouses().size() * 2) + 1)) {
                activePlayer.getPlayerStore().setNumberOfStones(activePlayer.getPlayerStore().getNumberOfStones() + 1);
                houseIndex = activePlayer.getHouses().size();
                // controls inActive players houses
            } else if (houseIndex < 0) {
                currentHouse = inActivePlayer.getHouses().get(houseIndex + (inActivePlayer.getHouses().size() + 1));
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
                // controls active players houses
            } else {
                currentHouse = activePlayer.getHouses().get(houseIndex);
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
            }

            if (numberOfStonesOnSelectedHouse == 1) {
                determineActivePlayerForNextTurn(activeGame, houseIndex);
                // TODO need to Look over is last stone placed into empty place owned by active player
            }
            houseIndex--;
            numberOfStonesOnSelectedHouse--;
        }
    }

    private void determineActivePlayerForNextTurn(Game game, int nextHouseIndex) {
        Player currentActivePlayer = game.getActivePlayer();
        Player currentInactivePlayer = game.getInactivePlayer();
        boolean lastStoneToActivePlayerStore = nextHouseIndex == currentActivePlayer.getHouses().size();

        if (!lastStoneToActivePlayerStore) {
            currentActivePlayer.setActivePlayer(false);
            currentInactivePlayer.setActivePlayer(true);
        }
    }

    private void checkIsGameOver(Game activeGame) {
        int remainingStonesOfFirstPlayer = activeGame.getFirstPlayer().getNumberOfStonesOnHouses();
        int remainingStonesOfSecondPlayer = activeGame.getSecondPlayer().getNumberOfStonesOnHouses();

        if (remainingStonesOfFirstPlayer == 0 || remainingStonesOfSecondPlayer == 0) {

            if (activeGame.getRemainingStonesInsertionEnabled()) {
                activeGame.getFirstPlayer().getPlayerStore()
                        .setNumberOfStones(activeGame.getFirstPlayer().getPlayerStore().getNumberOfStones() + remainingStonesOfFirstPlayer);
                activeGame.getSecondPlayer().getPlayerStore()
                        .setNumberOfStones(activeGame.getSecondPlayer().getPlayerStore().getNumberOfStones() + remainingStonesOfSecondPlayer);
            }

            if (activeGame.getFirstPlayer().getPlayerStore().getNumberOfStones()
                    > activeGame.getSecondPlayer().getPlayerStore().getNumberOfStones()) {
                activeGame.setWinnerPlayerId(activeGame.getFirstPlayer().getPlayerId());
            } else if (activeGame.getFirstPlayer().getPlayerStore().getNumberOfStones()
                    < activeGame.getSecondPlayer().getPlayerStore().getNumberOfStones()) {
                activeGame.setWinnerPlayerId(activeGame.getSecondPlayer().getPlayerId());
            } else {
                activeGame.setWinnerPlayerId(0);
            }
        }
    }

    private void lookOverEmptyCapture(Game activeGame, int nextHouseIndex) {
        // TODO
        Player currentActivePlayer = activeGame.getActivePlayer();
        Player currentInactivePlayer = activeGame.getInactivePlayer();
        House nextHouse;


        if (activeGame.getFlowsCounterClockwise() && nextHouseIndex > 0 && nextHouseIndex < currentActivePlayer.getHouses().size()) {
            nextHouse = currentActivePlayer.getHouses().get(nextHouseIndex);

            if (nextHouse.getNumberOfStones() == 0) {
                House acrossHouse = findAcrossHouse(activeGame, nextHouseIndex);

                if (activeGame.getEmptyCaptureEnabled()) {
                    currentActivePlayer.getPlayerStore()
                            .setNumberOfStones(currentActivePlayer.getPlayerStore().getNumberOfStones() + acrossHouse.getNumberOfStones() + 1);
                    acrossHouse.setNumberOfStones(0);
                    nextHouse.setNumberOfStones(nextHouse.getNumberOfStones() -1);
                } else {

                }

            }

        } else {

        }

        if (activeGame.getEmptyCaptureEnabled()) {
            if (activeGame.getFlowsCounterClockwise()) {






            } else {

            }

        } else {

        }
    }

    private House findAcrossHouse(Game activeGame, int houseIndexOfActivePlayer) {
        Player currentInactivePlayer = activeGame.getInactivePlayer();
        return currentInactivePlayer.getHouses().get(currentInactivePlayer.getHouses().size() - houseIndexOfActivePlayer + 1);
    }

}
