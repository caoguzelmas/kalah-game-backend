package com.caoguzelmas.kalah.controller;

import com.caoguzelmas.kalah.model.dto.GameRequestDto;
import com.caoguzelmas.kalah.model.dto.GameResponseDto;
import com.caoguzelmas.kalah.model.Game;
import com.caoguzelmas.kalah.service.IGameService;
import com.caoguzelmas.kalah.service.IMoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final IGameService gameService;
    private final IMoveService moveService;

    @PostMapping("/createGame")
    public ResponseEntity<GameResponseDto> createGame(@RequestBody GameRequestDto gameRequestDto) {
        Game game = gameService.createGame(gameRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(GameResponseDto.builder()
                .isSuccess(true)
                .message("Success")
                .game(game)
                .build());
    }

    @PutMapping("/move")
    public ResponseEntity<GameResponseDto> move(@RequestParam String gameId, @RequestParam Integer selectedHouseIndex) {
        Game game = moveService.move(gameId, selectedHouseIndex);

        return ResponseEntity.status(HttpStatus.OK).body(GameResponseDto.builder()
                .isSuccess(true)
                .message("Success")
                .game(game)
                .build());
    }

    @GetMapping("/getAllGames")
    public ResponseEntity<GameResponseDto> getAllGames() {
        List<Game> gameList = gameService.getAllGames();

        return ResponseEntity.status(HttpStatus.OK).body(GameResponseDto.builder()
                .isSuccess(true)
                .message("Success")
                .gameList(gameList)
                .build());
    }

    @GetMapping("/getGame")
    public ResponseEntity<GameResponseDto> getGame(@RequestParam String gameId) {
        Game game = gameService.getGame(gameId);

        return ResponseEntity.status(HttpStatus.OK).body(GameResponseDto.builder()
                .isSuccess(true)
                .message("Success")
                .game(game)
                .build());
    }
}
