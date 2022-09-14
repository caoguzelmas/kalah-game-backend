package com.caoguzelmas.kalah.controller;

import com.caoguzelmas.kalah.model.dto.GameRequestDto;
import com.caoguzelmas.kalah.model.dto.GameResponseDto;
import com.caoguzelmas.kalah.exceptions.GameException;
import com.caoguzelmas.kalah.exceptions.IllegalMoveException;
import com.caoguzelmas.kalah.model.Game;
import com.caoguzelmas.kalah.service.IGameService;
import com.caoguzelmas.kalah.service.IMoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final IGameService gameService;
    private final IMoveService moveService;

    @PostMapping("/createGame")
    public ResponseEntity<GameResponseDto> createGame(@RequestBody GameRequestDto gameRequestDto) {
        GameResponseDto createResponse = new GameResponseDto();
        createResponse.setSuccess(true);
        createResponse.setMessage("Success");
        createResponse.setGame(gameService.createGame(gameRequestDto));
        return ResponseEntity.status(HttpStatus.OK).body(createResponse);
    }

    @PutMapping("/move")
    public ResponseEntity<GameResponseDto> move(@RequestParam String gameId, @RequestParam Integer selectedHouseIndex) {
        GameResponseDto moveResponse = new GameResponseDto();

        try {
            Game game = moveService.move(gameId, selectedHouseIndex);

            moveResponse.setSuccess(true);
            moveResponse.setMessage("Success");
            moveResponse.setGame(game);

            return ResponseEntity.status(HttpStatus.OK).body(moveResponse);
        } catch (IllegalMoveException illegalMove) {
            moveResponse.setSuccess(false);
            moveResponse.setMessage(illegalMove.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(moveResponse);
        } catch (GameException gameException) {
            moveResponse.setSuccess(false);
            moveResponse.setMessage(gameException.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(moveResponse);
        }
    }

    @GetMapping("/getAllGames")
    public ResponseEntity<GameResponseDto> getAllGames() {
        GameResponseDto gameListResponse = new GameResponseDto();
        gameListResponse.setSuccess(true);
        gameListResponse.setMessage("Success");
        gameListResponse.setGameList(gameService.getAllGames());

        return ResponseEntity.status(HttpStatus.OK).body(gameListResponse);
    }

    @GetMapping("/getGame")
    public ResponseEntity<GameResponseDto> getGame(@RequestParam String gameId) {
        GameResponseDto gameResponse = new GameResponseDto();

        try {
            Game game = gameService.getGame(gameId);

            gameResponse.setSuccess(true);
            gameResponse.setMessage("Success");
            gameResponse.setGame(game);

            return ResponseEntity.status(HttpStatus.OK).body(gameResponse);
        } catch (GameException gameException) {
            gameResponse.setSuccess(false);
            gameResponse.setMessage(gameException.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameResponse);
        }
    }
}
