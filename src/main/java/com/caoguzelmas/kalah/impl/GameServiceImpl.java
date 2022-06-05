package com.caoguzelmas.kalah.impl;

import com.caoguzelmas.kalah.dto.GameRequestDto;
import com.caoguzelmas.kalah.model.Game;
import com.caoguzelmas.kalah.model.Player;
import com.caoguzelmas.kalah.model.Store;
import com.caoguzelmas.kalah.repository.GameRepository;
import com.caoguzelmas.kalah.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements IGameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game createGame(GameRequestDto gameRequestDto) {
        Player firstPlayerOfNewGame =  new Player(
                gameRequestDto.getNumberOfHouses(),
                gameRequestDto.getNumberOfStones(),
                1,
                new Store(1),
                gameRequestDto.getFirstMoveOnFirstPlayer());
        Player secondPlayerOfNewGame =  new Player(
                gameRequestDto.getNumberOfHouses(),
                gameRequestDto.getNumberOfStones(),
                2,
                new Store(2),
                !gameRequestDto.getFirstMoveOnFirstPlayer());
        Game newGame = new Game(
                firstPlayerOfNewGame,
                secondPlayerOfNewGame,
                gameRequestDto.getFlowsCounterClockwise(),
                gameRequestDto.getEmptyCaptureEnabled());

        return gameRepository.saveGame(newGame);
    }
}
