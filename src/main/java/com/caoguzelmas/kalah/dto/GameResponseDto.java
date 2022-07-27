package com.caoguzelmas.kalah.dto;

import com.caoguzelmas.kalah.model.Game;

import java.util.List;

public class GameResponseDto {

    private Game game;
    private List<Game> gameList;
    private boolean isSuccess;
    private String message;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
