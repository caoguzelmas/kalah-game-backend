package com.caoguzelmas.kalah.model.dto;

import com.caoguzelmas.kalah.model.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameResponseDto {

    private Game game;
    private List<Game> gameList;
    private boolean isSuccess;
    private String message;
}
