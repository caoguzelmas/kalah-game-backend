package com.caoguzelmas.kalah.service.impl;

import com.caoguzelmas.kalah.exceptions.GameException;
import com.caoguzelmas.kalah.model.dto.GameRequestDto;
import com.caoguzelmas.kalah.model.Game;
import com.caoguzelmas.kalah.model.Player;
import com.caoguzelmas.kalah.repository.GameRepository;
import com.caoguzelmas.kalah.service.IGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements IGameService {
    private final GameRepository gameRepository;
    @Override
    public Game createGame(GameRequestDto gameRequestDto) {

        Game newGame = new Game(
                Player.builder()
                        .playerId(1)
                        .isActivePlayer(gameRequestDto.getFirstMoveOnFirstPlayer())
                        .build(),
                Player.builder()
                        .playerId(2)
                        .isActivePlayer(!gameRequestDto.getFirstMoveOnFirstPlayer())
                        .build(),
                gameRequestDto.getNumberOfHouses(),
                gameRequestDto.getNumberOfStones(),
                gameRequestDto.getFlowsCounterClockwise(),
                gameRequestDto.getEmptyCaptureEnabled(),
                gameRequestDto.getRemainingStonesInsertionEnabled());

        return gameRepository.save(newGame);
    }

    @Override
    public Game getGame(String gameId) {
        Optional<Game> game = gameRepository.findById(gameId);

        if (game.isPresent()) {
            return game.get();
        } else {
            throw new GameException("Game not found with Given Game ID: " + gameId);
        }
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
    }
}
