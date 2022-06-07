package com.caoguzelmas.kalah.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Player {

    private Integer playerId;
    private Boolean isActivePlayer;

    public Player(Integer playerId, Boolean isActivePlayer) {
        this.playerId = playerId;
        this.isActivePlayer = isActivePlayer;
    }

    public Player(int playerId) {
        this.playerId = playerId;
    }


    /*@JsonIgnore
    public int getNumberOfStonesOnHouses() {
        return houses.stream().mapToInt(House::getNumberOfStones).sum();
    }*/

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
