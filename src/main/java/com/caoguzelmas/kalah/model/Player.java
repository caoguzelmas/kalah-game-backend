package com.caoguzelmas.kalah.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Integer playerId;
    private Store playerStore;
    private List<House> houses;
    private Boolean isActivePlayer;

    public Player(int expectedSizeOfHouseList, int expectedNumberOfStones,
                  Integer playerId, Store playerStore, Boolean isActivePlayer) {
        generateHouseList(expectedSizeOfHouseList, expectedNumberOfStones);
        this.playerId = playerId;
        this.playerStore = playerStore;
        this.isActivePlayer = isActivePlayer;
    }

    public Player(int playerId) {
        this.playerId = playerId;
    }

    private void generateHouseList(int expectedSizeOfHouseList, int expectedNumberOfStones) {
        this.houses = new ArrayList<>();
        for (int i = 0; i <= expectedSizeOfHouseList-1; i++) {
            this.houses.add(new House(i, expectedNumberOfStones));
        }
    }

    @JsonIgnore
    public int getNumberOfStonesOnHouses() {
        return houses.stream().mapToInt(House::getNumberOfStones).sum();
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

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public Boolean getActivePlayer() {
        return isActivePlayer;
    }

    public void setActivePlayer(Boolean activePlayer) {
        isActivePlayer = activePlayer;
    }
}
