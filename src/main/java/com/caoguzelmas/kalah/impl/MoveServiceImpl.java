package com.caoguzelmas.kalah.impl;

import com.caoguzelmas.kalah.exceptions.IllegalMoveException;
import com.caoguzelmas.kalah.model.Game;
import com.caoguzelmas.kalah.model.House;
import com.caoguzelmas.kalah.model.Player;
import com.caoguzelmas.kalah.repository.GameRepository;
import com.caoguzelmas.kalah.service.IMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MoveServiceImpl implements IMoveService {

    private Game activeGame;
    private ArrayList<House> houseList;
    private House currentHouse;
    private Player activePlayer;
    private int storeIndexOfFirstPlayer;
    private int storeIndexOfSecondPlayer;
    private int storeIndexOfActivePlayer;
    private int storeIndexOfInactivePlayer;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game move(String gameId, int selectedHouseIndex) {
        Optional<Game> game = gameRepository.findById(gameId);

        if (game.isPresent()) {
            activeGame = game.get();
            validateMove(selectedHouseIndex);
            replaceStones(selectedHouseIndex);
            checkIsGameOver();
            return gameRepository.save(activeGame);
        }

        return null;
    }

    private void validateMove(int selectedHouseIndex) {
        activePlayer = activeGame.getActivePlayer();
        storeIndexOfFirstPlayer = ((activeGame.getGameBoard().getHouses().size() - 2) / 2);
        storeIndexOfSecondPlayer = activeGame.getGameBoard().getHouses().size() - 1;

        if (selectedHouseIndex == storeIndexOfFirstPlayer || selectedHouseIndex == storeIndexOfSecondPlayer) {
            throw new IllegalMoveException("Store selection does not allowed!");
        }

        if (selectedHouseIndex < 0 || selectedHouseIndex >= activeGame.getGameBoard().getHouses().size()) {
            throw new IllegalMoveException("Selected Index does not exist!");
        }

        if (activeGame.getGameBoard().getHouses().get(selectedHouseIndex).getNumberOfStones() == 0) {
            throw new IllegalMoveException("Selected house is empty!");
        }

        currentHouse = activeGame.getGameBoard().getHouses().get(selectedHouseIndex);
        if (!activePlayer.getPlayerId().equals(currentHouse.getOwnedPlayerId())) {
            throw new IllegalMoveException("It's other players turn!");
        }
    }

    private void replaceStones(int houseIndex) {
        currentHouse = activeGame.getGameBoard().getHouses().get(houseIndex);

        if (activeGame.getGameVariation().getFlowsCounterClockwise()) {
            replaceStonesByCounterClockWise(currentHouse);
        } else {
            replaceStonesByClockWise(currentHouse);
        }
    }

    private void replaceStonesByCounterClockWise(House currentHouse) {
        int houseIndex = currentHouse.getHouseId();
        storeIndexOfInactivePlayer = activeGame.getStoreIndexOfInactivePlayer();
        int numberOfStonesOnSelectedHouse = activeGame.getGameBoard().getHouses().get(houseIndex).getNumberOfStones();
        currentHouse.setNumberOfStones(0);

        houseIndex +=1;

        while(numberOfStonesOnSelectedHouse > 0) {

           if (houseIndex == storeIndexOfInactivePlayer) {

                if (storeIndexOfInactivePlayer == activeGame.getGameBoard().getHouseIdOfLastHouse()) {
                    houseIndex = 0;
                } else {
                    houseIndex += 1;
                }
            }

           if (houseIndex > activeGame.getGameBoard().getHouseIdOfLastHouse()) {
               houseIndex = 0;
           }

            currentHouse = activeGame.getGameBoard().getHouses().get(houseIndex);
            currentHouse.setNumberOfStones(currentHouse.getNumberOfStones() + 1);
            houseIndex++;
            numberOfStonesOnSelectedHouse--;
        }
        int houseIndexOfLastStone = houseIndex-1;
        lookOverEmptyCapture(houseIndexOfLastStone);
        determineActivePlayerForNextTurn(houseIndexOfLastStone);
    }

    private void replaceStonesByClockWise(House currentHouse) {
        int houseIndex = currentHouse.getHouseId();
        storeIndexOfInactivePlayer = activeGame.getStoreIndexOfInactivePlayer();
        int numberOfStonesOnSelectedHouse = activeGame.getGameBoard().getHouses().get(houseIndex).getNumberOfStones();
        currentHouse.setNumberOfStones(0);
        houseIndex -=1;

        while (numberOfStonesOnSelectedHouse > 0) {

            if (houseIndex < 0) {
                houseIndex = activeGame.getActivePlayer().getPlayerId().equals(1) ?
                        activeGame.getGameBoard().getHouseIdOfLastHouse() - 1 :
                        activeGame.getGameBoard().getHouseIdOfLastHouse();
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
        lookOverEmptyCapture(houseIndexOfLastStone);
        determineActivePlayerForNextTurn(houseIndexOfLastStone);
    }

    private void determineActivePlayerForNextTurn(int houseIndexOfLastStone) {
        storeIndexOfActivePlayer = activeGame.getStoreIndexOfActivePlayer();
        boolean lastStoneToActivePlayerStore = houseIndexOfLastStone == storeIndexOfActivePlayer;

        if (!lastStoneToActivePlayerStore) {
            activePlayer = activeGame.getActivePlayer();
            Player inActivePlayer = activeGame.getInactivePlayer();
            activePlayer.setActivePlayer(false);
            inActivePlayer.setActivePlayer(true);
        }
    }

    private void checkIsGameOver() {
        storeIndexOfFirstPlayer = activeGame.getStoreIndexOfFirstPlayer();
        storeIndexOfSecondPlayer = activeGame.getStoreIndexOfSecondPlayer();
        int remainingStonesOfFirstPlayer = activeGame.getGameBoard().getRemainingStonesOfFirstPlayer(storeIndexOfFirstPlayer);
        int remainingStonesOfSecondPlayer = activeGame.getGameBoard().getRemainingStonesOfSecondPlayer(storeIndexOfSecondPlayer);

        houseList = activeGame.getGameBoard().getHouses();

        if (remainingStonesOfFirstPlayer == 0 || remainingStonesOfSecondPlayer == 0) {

            if (activeGame.getGameVariation().getRemainingStonesInsertionEnabled()) {
                houseList.get(storeIndexOfFirstPlayer)
                        .setNumberOfStones(houseList.get(storeIndexOfFirstPlayer).getNumberOfStones() + remainingStonesOfFirstPlayer);
                houseList.get(storeIndexOfSecondPlayer)
                        .setNumberOfStones(houseList.get(storeIndexOfSecondPlayer).getNumberOfStones() + remainingStonesOfSecondPlayer);
                activeGame.getGameBoard().setHousesToEmpty(storeIndexOfFirstPlayer, storeIndexOfSecondPlayer);
            }

            if (activeGame.getGameBoard().getHouses().get(storeIndexOfFirstPlayer).getNumberOfStones()
                    > activeGame.getGameBoard().getHouses().get(storeIndexOfSecondPlayer).getNumberOfStones()) {
                activeGame.setWinnerPlayerId(activeGame.getFirstPlayer().getPlayerId());
            } else if (activeGame.getGameBoard().getHouses().get(storeIndexOfFirstPlayer).getNumberOfStones()
                    < activeGame.getGameBoard().getHouses().get(storeIndexOfSecondPlayer).getNumberOfStones()) {
                activeGame.setWinnerPlayerId(activeGame.getSecondPlayer().getPlayerId());
            } else {
                activeGame.setWinnerPlayerId(0);
            }
        }
    }

    private void lookOverEmptyCapture(int houseIndexOfLastStone) {
        storeIndexOfFirstPlayer = activeGame.getStoreIndexOfFirstPlayer();
        storeIndexOfSecondPlayer = activeGame.getStoreIndexOfSecondPlayer();
        activePlayer = activeGame.getActivePlayer();
        storeIndexOfActivePlayer = activeGame.getStoreIndexOfActivePlayer();
        houseList = activeGame.getGameBoard().getHouses();
        currentHouse = activeGame.getGameBoard().getHouses().get(houseIndexOfLastStone);

        if (currentHouse.getOwnedPlayerId().equals(activePlayer.getPlayerId())
                && (houseIndexOfLastStone != storeIndexOfFirstPlayer)
                && (houseIndexOfLastStone != storeIndexOfSecondPlayer)
                && currentHouse.getNumberOfStones().equals(1)) {
            House acrossHouse = findAcrossHouse(houseIndexOfLastStone);

            if (activeGame.getGameVariation().getEmptyCaptureEnabled() || !acrossHouse.getNumberOfStones().equals(0)) {
                houseList.get(storeIndexOfActivePlayer).setNumberOfStones(
                        houseList.get(activeGame.getStoreIndexOfActivePlayer()).getNumberOfStones()
                                + houseList.get(houseIndexOfLastStone).getNumberOfStones()
                                + acrossHouse.getNumberOfStones());
                houseList.get(houseIndexOfLastStone).setNumberOfStones(0);
                acrossHouse.setNumberOfStones(0);
            }
        }
    }

    private House findAcrossHouse(int houseIndex) {
        houseList = activeGame.getGameBoard().getHouses();
        activePlayer = activeGame.getActivePlayer();

        return houseList.get((houseList.size() - 2) - houseIndex);
    }

}
