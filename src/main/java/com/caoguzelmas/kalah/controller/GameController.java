package com.caoguzelmas.kalah.controller;

import com.caoguzelmas.kalah.dto.GameRequestDto;
import com.caoguzelmas.kalah.exceptions.IllegalMoveException;
import com.caoguzelmas.kalah.model.Game;
import com.caoguzelmas.kalah.service.IGameService;
import com.caoguzelmas.kalah.service.IMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private IGameService gameService;

    @Autowired
    private IMoveService moveService;


    @PostMapping("/createGame")
    public ResponseEntity<Game> createGame(@RequestBody GameRequestDto gameRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.createGame(gameRequestDto));
    }

    @PutMapping("/move")
    public ResponseEntity<Game> move(@RequestParam Integer gameId, @RequestParam Integer selectedHouseIndex) {
        Game moveResponse = new Game();
        try {
            moveResponse = moveService.move(gameId, selectedHouseIndex);

            return ResponseEntity.status(HttpStatus.OK).body(moveResponse);
        } catch (IllegalMoveException illegalMove) {
            moveResponse.setSuccess(false);
            moveResponse.setMessage(illegalMove.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(moveResponse);
        }
    }
}
