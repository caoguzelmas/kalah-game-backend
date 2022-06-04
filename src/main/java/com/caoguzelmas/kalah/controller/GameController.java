package com.caoguzelmas.kalah.controller;

import com.caoguzelmas.kalah.dto.GameRequestDto;
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
        return ResponseEntity.status(HttpStatus.OK).body(moveService.move(gameId, selectedHouseIndex));
    }
}
