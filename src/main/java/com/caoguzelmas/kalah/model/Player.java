package com.caoguzelmas.kalah.model;

public class Player {

    private Integer playerId;
    private Boolean isActivePlayer;

    public Player(Integer playerId, Boolean isActivePlayer) {
        this.playerId = playerId;
        this.isActivePlayer = isActivePlayer;
    }

    public Player() {
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Boolean getActivePlayer() {
        return isActivePlayer;
    }

    public void setActivePlayer(Boolean activePlayer) {
        isActivePlayer = activePlayer;
    }
}
