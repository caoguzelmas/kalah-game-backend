package com.caoguzelmas.kalah.impl;

import com.caoguzelmas.kalah.dto.GameRequestDto;
import com.caoguzelmas.kalah.model.Game;
import com.caoguzelmas.kalah.model.Player;
import com.caoguzelmas.kalah.model.ResponseCode;
import com.caoguzelmas.kalah.repository.GameRepository;
import com.caoguzelmas.kalah.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements IGameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game createGame(GameRequestDto gameRequestDto) {
        Player firstPlayerOfNewGame =  new Player(1, gameRequestDto.getFirstMoveOnFirstPlayer());
        Player secondPlayerOfNewGame =  new Player(2, !gameRequestDto.getFirstMoveOnFirstPlayer());
        Game newGame = new Game(
                firstPlayerOfNewGame,
                secondPlayerOfNewGame,
                gameRequestDto.getNumberOfHouses(),
                gameRequestDto.getNumberOfStones(),
                gameRequestDto.getFlowsCounterClockwise(),
                gameRequestDto.getEmptyCaptureEnabled(),
                gameRequestDto.getRemainingStonesInsertionEnabled(),
                new ResponseCode());

        return gameRepository.saveGame(newGame);
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }

    @Override
    public Game getGame(int gameId) {
        return gameRepository.findGame(gameId);
    }
}
