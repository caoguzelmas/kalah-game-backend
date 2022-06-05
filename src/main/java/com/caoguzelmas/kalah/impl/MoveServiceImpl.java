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
        // TODO need to Look over is last stone placed into empty place owned by active player
        checkIsGameOver(activeGame);

        return gameRepository.saveGame(activeGame);
    }

    private void validateMove(int gameId, int selectedHouseIndex) {
        Game activeGame = gameRepository.findGame(gameId);

        if (selectedHouseIndex < 0 || selectedHouseIndex > activeGame.getFirstPlayer().getHouses().size()) {
            throw new IllegalMoveException("Selected Index does not exist!");
        }

        if (activeGame.getActivePlayer().getHouses().get(selectedHouseIndex-1).getNumberOfStones() == 0) {
            throw new IllegalMoveException("Selected house is empty!");
        }
    }

    private void replaceStones(Game activeGame, int houseIndex) {
        Player activePlayer = activeGame.getActivePlayer();
        Player inActivePlayer = activeGame.getInactivePlayer();
        House selectedHouseOfActivePlayer = activePlayer.getHouses().get(houseIndex-1);
        int numberOfStonesOnSelectedHouse = selectedHouseOfActivePlayer.getNumberOfStones();
        selectedHouseOfActivePlayer.setNumberOfStones(0);

        if (activeGame.getFlowsCounterClockwise()) {
            houseIndex++;
            replaceStonesByCounterClockWise(houseIndex, numberOfStonesOnSelectedHouse, activePlayer, inActivePlayer, activeGame);
        } else {
            houseIndex--;
            replaceStonesByClockWise(houseIndex, numberOfStonesOnSelectedHouse, activePlayer, inActivePlayer, activeGame);
        }
    }

    private void replaceStonesByCounterClockWise(int houseIndex, int numberOfStonesOnSelectedHouse,
                                                 Player activePlayer, Player inActivePlayer, Game activeGame) {
        House currentHouse;
        for (int i = numberOfStonesOnSelectedHouse; i > 0; i--) {
            // controls active players store"
            if (houseIndex == activePlayer.getHouses().size() + 1) {
                activePlayer.getPlayerStore().setNumberOfStones(activePlayer.getPlayerStore().getNumberOfStones() + 1);
            // controls inActive players store
            } else if (houseIndex > (activePlayer.getHouses().size() * 2) + 1) {
                houseIndex = 1;
                currentHouse = activePlayer.getHouses().get(houseIndex-1);
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
            // controls inActive players houses
            } else if (houseIndex > activePlayer.getHouses().size()) {
                int inActivePlayerHouseIndex = houseIndex - (activePlayer.getHouses().size() + 1);
                currentHouse = inActivePlayer.getHouses().get(inActivePlayerHouseIndex-1);
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
            // controls active players houses
            } else {
                currentHouse = activePlayer.getHouses().get(houseIndex-1);
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
            }

            if (i == 1) {
                determineActivePlayerForNextTurn(activeGame, houseIndex);
               // lookOverEmptyCapture(activeGame, houseIndex);

            }
            houseIndex++;
        }
    }

    private void replaceStonesByClockWise(int houseIndex, int numberOfStonesOnSelectedHouse,
                                          Player activePlayer, Player inActivePlayer, Game activeGame) {
        House currentHouse;

        for (int i = numberOfStonesOnSelectedHouse; i > 0; i--) {
            // controls inActive players store
            if (houseIndex == 0) {
                houseIndex = -1;
                currentHouse = inActivePlayer.getHouses().get((houseIndex + (inActivePlayer.getHouses().size() + 1)) - 1);
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
             // controls active players store
            } else if (houseIndex == (-activePlayer.getHouses().size() - 1)) {
                activePlayer.getPlayerStore().setNumberOfStones(activePlayer.getPlayerStore().getNumberOfStones() + 1);
                houseIndex = activePlayer.getHouses().size() + 1;
            // controls inActive players houses
            } else if (houseIndex < 0) {
                currentHouse = inActivePlayer.getHouses().get((houseIndex + (inActivePlayer.getHouses().size() + 1)) - 1);
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
            // controls active players houses
            } else {
                currentHouse = activePlayer.getHouses().get(houseIndex-1);
                currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
            }

            if (i == 1) {
                determineActivePlayerForNextTurn(activeGame, houseIndex);
            }
            houseIndex--;
        }
    }

    private void determineActivePlayerForNextTurn(Game game, int nextHouseIndex) {
        Player currentActivePlayer = game.getActivePlayer();
        Player currentInactivePlayer = game.getInactivePlayer();
        boolean lastStoneToActivePlayerStore = nextHouseIndex == (currentActivePlayer.getHouses().size() + 1);

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

        if (activeGame.getEmptyCaptureEnabled()) {
            if (activeGame.getFlowsCounterClockwise()) {





            } else {

            }

        } else {

        }
    }

}
