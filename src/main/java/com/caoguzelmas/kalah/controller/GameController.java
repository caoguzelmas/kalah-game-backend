package com.caoguzelmas.kalah.controller;

import com.caoguzelmas.kalah.dto.GameRequestDto;
import com.caoguzelmas.kalah.model.Game;
import com.caoguzelmas.kalah.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private IGameService gameService;

    @PostMapping("/createGame")
    public ResponseEntity<Game> createGame(@RequestBody GameRequestDto gameRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.createGame(gameRequestDto));
    }
}
