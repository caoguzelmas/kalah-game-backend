package com.caoguzelmas.kalah.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Integer gameId;
    private List<House> houses;
    private Player firstPlayer;
    private Player secondPlayer;

    public Game(int expectedSizeOfHouseList, int expectedNumberOfStones, Player firstPlayer, Player secondPlayer) {
        this.gameId = hashCode();
        generateHouseList(expectedSizeOfHouseList, expectedNumberOfStones);
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    private void generateHouseList(int expectedSizeOfHouseList, int expectedNumberOfStones) {
        this.houses = new ArrayList<>();
        for (int i = 1; i <= expectedSizeOfHouseList; i++) {
            this.houses.add(new House(i, expectedNumberOfStones));
        }
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
}
