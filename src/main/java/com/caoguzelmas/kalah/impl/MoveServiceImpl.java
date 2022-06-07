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
        // TODO
        //checkIsGameOver(activeGame);

        return gameRepository.saveGame(activeGame);
    }

    private void validateMove(int gameId, int selectedHouseIndex) {
        Game activeGame = gameRepository.findGame(gameId);
        Player activePlayer = activeGame.getActivePlayer();
        int storeIndexOfFirstPlayer = ((activeGame.getGameBoard().getHouses().size() - 2) / 2);
        int storeIndexOfSecondPlayer = activeGame.getGameBoard().getHouses().size() - 1;

        if (selectedHouseIndex == storeIndexOfFirstPlayer || selectedHouseIndex == storeIndexOfSecondPlayer) {
            throw new IllegalMoveException("Store selection does not allowed!");
        }

        if (selectedHouseIndex < 0 || selectedHouseIndex > activeGame.getGameBoard().getHouses().size()) {
            throw new IllegalMoveException("Selected Index does not exist!");
        }

        if (activeGame.getGameBoard().getHouses().get(selectedHouseIndex).getNumberOfStones() == 0) {
            throw new IllegalMoveException("Selected house is empty!");
        }

        House selectedHouse = activeGame.getGameBoard().getHouses().get(selectedHouseIndex);
        if (!activePlayer.getPlayerId().equals(selectedHouse.getOwnedPlayerId())) {
            throw new IllegalMoveException("It's other players turn!");
        }
    }

    private void replaceStones(Game activeGame, int houseIndex) {
        House selectedHouse = activeGame.getGameBoard().getHouses().get(houseIndex);
        int numberOfStonesOnSelectedHouse = selectedHouse.getNumberOfStones();
        selectedHouse.setNumberOfStones(0);

        if (activeGame.getFlowsCounterClockwise()) {
            int nextHouseIndex = houseIndex+1;
            replaceStonesByCounterClockWise(nextHouseIndex, numberOfStonesOnSelectedHouse, activeGame);
        } else {
            int previousHouseIndex = houseIndex-1;
            replaceStonesByClockWise(previousHouseIndex, numberOfStonesOnSelectedHouse, activeGame);
        }
    }

    private void replaceStonesByCounterClockWise(int houseIndex, int numberOfStonesOnSelectedHouse, Game activeGame) {
        House currentHouse;
        int storeIndexOfInactivePlayer = activeGame.getInactivePlayer().getPlayerId().equals(1) ?
                ((activeGame.getGameBoard().getHouses().size() - 2) / 2) :
                activeGame.getGameBoard().getHouses().size() - 1;

        while(numberOfStonesOnSelectedHouse > 0) {
           if (houseIndex == storeIndexOfInactivePlayer) {

                if (storeIndexOfInactivePlayer == activeGame.getGameBoard().getLastHouseOfList().getHouseId()) {
                    houseIndex = 0;
                } else {
                    houseIndex += 1;
                }
            }
            currentHouse = activeGame.getGameBoard().getHouses().get(houseIndex);
            currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
            houseIndex++;
            numberOfStonesOnSelectedHouse--;
        }
        int houseIndexOfLastStone = houseIndex-1;
        determineActivePlayerForNextTurn(activeGame, houseIndexOfLastStone);
    }

    private void replaceStonesByClockWise(int houseIndex, int numberOfStonesOnSelectedHouse, Game activeGame) {
        House currentHouse;
        int storeIndexOfInactivePlayer = activeGame.getInactivePlayer().getPlayerId().equals(1) ?
                ((activeGame.getGameBoard().getHouses().size() - 2) / 2) :
                activeGame.getGameBoard().getHouses().size() - 1;

        while (numberOfStonesOnSelectedHouse > 0) {

            if (houseIndex < 0) {
                houseIndex = activeGame.getActivePlayer().getPlayerId().equals(1) ?
                        activeGame.getGameBoard().getLastHouseOfList().getHouseId() - 1 :
                        activeGame.getGameBoard().getLastHouseOfList().getHouseId();
            }

            if (houseIndex == storeIndexOfInactivePlayer) {
                houseIndex -= 1;
            }

            currentHouse = activeGame.getGameBoard().getHouses().get(houseIndex);
            currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
            houseIndex--;
            numberOfStonesOnSelectedHouse--;
        }
        int houseIndexOfLastStone = houseIndex+1;
        determineActivePlayerForNextTurn(activeGame, houseIndexOfLastStone);
    }

    private void determineActivePlayerForNextTurn(Game game, int houseIndexOfLastStone) {
        int storeIndexOfActivePlayer = game.getStoreIndexOfActivePlayer();
        boolean lastStoneToActivePlayerStore = houseIndexOfLastStone == storeIndexOfActivePlayer;

        if (!lastStoneToActivePlayerStore) {
            Player currentActivePlayer = game.getActivePlayer();
            Player currentInactivePlayer = game.getInactivePlayer();
            currentActivePlayer.setActivePlayer(false);
            currentInactivePlayer.setActivePlayer(true);
        }
    }

    //TODO
    /*private void checkIsGameOver(Game activeGame) {
        int remainingStonesOfFirstPlayer = activeGame.getGameBoard().getRemainingStonesOfFirstPlayer();
        int remainingStonesOfSecondPlayer = activeGame.getGameBoard().getRemainingStonesOfSecondPlayer();

        if (remainingStonesOfFirstPlayer == 0 || remainingStonesOfSecondPlayer == 0) {

            if (activeGame.getRemainingStonesInsertionEnabled()) {
                activeGame.getGameBoard().getHouses().get(activeGame.getStoreIndexOfActivePlayer())
                        .setNumberOfStones(activeGame.getGameBoard().getHouses().get(activeGame.getStoreIndexOfActivePlayer()).getNumberOfStones() + remainingStonesOfFirstPlayer);
                activeGame.getGameBoard().getHouses().get(activeGame.getStoreIndexOfActivePlayer())
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
    }*/

    /*private void lookOverEmptyCapture(Game activeGame, int nextHouseIndex) {
        // TODO
        Player currentActivePlayer = activeGame.getActivePlayer();
        Player currentInactivePlayer = activeGame.getInactivePlayer();
        House nextHouse = currentActivePlayer.getHouses().get(nextHouseIndex);

        if (nextHouse.getNumberOfStones() == 0) {

            if (activeGame.getFlowsCounterClockwise() && nextHouseIndex < currentActivePlayer.getHouses().size()) {

                if (!activeGame.getEmptyCaptureEnabled()) {



                } else {

                }


            }



        }


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
    }*/

}
