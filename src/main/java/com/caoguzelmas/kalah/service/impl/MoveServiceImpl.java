package com.caoguzelmas.kalah.service.impl;

import com.caoguzelmas.kalah.exceptions.GameException;
import com.caoguzelmas.kalah.exceptions.IllegalMoveException;
import com.caoguzelmas.kalah.model.Game;
import com.caoguzelmas.kalah.model.House;
import com.caoguzelmas.kalah.model.Player;
import com.caoguzelmas.kalah.repository.GameRepository;
import com.caoguzelmas.kalah.service.IMoveService;
import com.caoguzelmas.kalah.service.rule.ReplaceStonesStrategy;
import com.caoguzelmas.kalah.service.rule.impl.ReplaceStonesByClockwise;
import com.caoguzelmas.kalah.service.rule.impl.ReplaceStonesByCounterClockwise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoveServiceImpl implements IMoveService {
    private final GameRepository gameRepository;

    @Override
    public Game move(String gameId, int selectedHouseIndex) {
        Optional<Game> game = gameRepository.findById(gameId);

        if (game.isPresent()) {
            Game activeGame = game.get();
            validateMove(selectedHouseIndex, activeGame);
            ReplaceStonesStrategy replaceStonesStrategy = activeGame.getGameVariation().getFlowsCounterClockwise() ?
                    new ReplaceStonesByCounterClockwise() :
                    new ReplaceStonesByClockwise();
            replaceStones(activeGame, selectedHouseIndex, replaceStonesStrategy);
            checkIsGameOver(activeGame);
            return gameRepository.save(activeGame);
        } else {
            throw new GameException("Game not found with Given Game ID: " + gameId);
        }
    }

    private void validateMove(int selectedHouseIndex, Game activeGame) {
        Player activePlayer = activeGame.getActivePlayer();
        int storeIndexOfFirstPlayer = activeGame.getStoreIndexOfFirstPlayer();
        int storeIndexOfSecondPlayer = activeGame.getStoreIndexOfSecondPlayer();

        if (selectedHouseIndex == storeIndexOfFirstPlayer || selectedHouseIndex == storeIndexOfSecondPlayer) {
            throw new IllegalMoveException("Store selection does not allowed!");
        }

        if (selectedHouseIndex < 0 || selectedHouseIndex >= activeGame.getGameBoard().getHouses().size()) {
            throw new IllegalMoveException("Selected Index does not exist!");
        }

        if (activeGame.getGameBoard().getHouses().get(selectedHouseIndex).getNumberOfStones() == 0) {
            throw new IllegalMoveException("Selected house is empty!");
        }

        House currentHouse = activeGame.getGameBoard().getHouses().get(selectedHouseIndex);
        if (!activePlayer.getPlayerId().equals(currentHouse.getOwnedPlayerId())) {
            throw new IllegalMoveException("It's other players turn!");
        }
    }

    private void replaceStones(Game activeGame, Integer houseIndex, ReplaceStonesStrategy replaceStonesStrategy) {
        int houseIndexOfLastStone = replaceStonesStrategy.replaceStones(activeGame, houseIndex);
        lookOverEmptyCapture(houseIndexOfLastStone, activeGame);
        determineActivePlayerForNextTurn(houseIndexOfLastStone, activeGame);
    }

    private void determineActivePlayerForNextTurn(int houseIndexOfLastStone, Game activeGame) {

        if (houseIndexOfLastStone != activeGame.getStoreIndexOfActivePlayer()) {
            Player activePlayer = activeGame.getActivePlayer();
            Player inActivePlayer = activeGame.getInactivePlayer();
            activePlayer.setIsActivePlayer(false);
            inActivePlayer.setIsActivePlayer(true);
        }
    }

    private void checkIsGameOver(Game activeGame) {
        int storeIndexOfFirstPlayer = activeGame.getStoreIndexOfFirstPlayer();
        int storeIndexOfSecondPlayer = activeGame.getStoreIndexOfSecondPlayer();
        int remainingStonesOfFirstPlayer = activeGame.getGameBoard().getRemainingStonesOfFirstPlayer(storeIndexOfFirstPlayer);
        int remainingStonesOfSecondPlayer = activeGame.getGameBoard().getRemainingStonesOfSecondPlayer(storeIndexOfSecondPlayer);

        ArrayList<House> houseList = activeGame.getGameBoard().getHouses();

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

    private void lookOverEmptyCapture(int houseIndexOfLastStone, Game activeGame) {
        ArrayList<House> houseList = activeGame.getGameBoard().getHouses();
        House currentHouse = activeGame.getGameBoard().getHouses().get(houseIndexOfLastStone);

        if (currentHouse.getOwnedPlayerId().equals(activeGame.getActivePlayer().getPlayerId())
                && (houseIndexOfLastStone != activeGame.getStoreIndexOfFirstPlayer())
                && (houseIndexOfLastStone != activeGame.getStoreIndexOfSecondPlayer())
                && currentHouse.getNumberOfStones().equals(1)) {
            House acrossHouse = activeGame.getGameBoard().findAcrossHouse(houseIndexOfLastStone);

            if (activeGame.getGameVariation().getEmptyCaptureEnabled() || !acrossHouse.getNumberOfStones().equals(0)) {

                houseList.get(activeGame.getStoreIndexOfActivePlayer())
                        .addStones(houseList.get(houseIndexOfLastStone).getNumberOfStones() + acrossHouse.getNumberOfStones());
                houseList.get(houseIndexOfLastStone).setNumberOfStones(0);
                acrossHouse.setNumberOfStones(0);
            }
        }
    }
}
