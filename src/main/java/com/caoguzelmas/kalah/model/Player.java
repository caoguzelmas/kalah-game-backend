package com.caoguzelmas.kalah.model;

public class Player {

    private Integer playerId;
    private Store playerStore;
    private Boolean isActivePlayer;

    public Player(Integer playerId, Store playerStore, Boolean isActivePlayer) {
        this.playerId = playerId;
        this.playerStore = playerStore;
        this.isActivePlayer = isActivePlayer;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Store getPlayerStore() {
        return playerStore;
    }

    public void setPlayerStore(Store playerStore) {
        this.playerStore = playerStore;
    }

    public Boolean getActivePlayer() {
        return isActivePlayer;
    }

    public void setActivePlayer(Boolean activePlayer) {
        isActivePlayer = activePlayer;
    }
}
