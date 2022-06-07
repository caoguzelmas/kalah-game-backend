package com.caoguzelmas.kalah.model;

import java.util.ArrayList;

public class GameBoard {

    private ArrayList<House> houses;

    public GameBoard(int expectedSizeOfHouseList, int expectedNumberOfStones) {
        this.generateHouseList(expectedSizeOfHouseList, expectedNumberOfStones);
    }

    private void generateHouseList(int expectedSizeOfHouseList, int expectedNumberOfStones) {
        this.houses = new ArrayList<>();

        // Houses of Player1
        for (int i = 0; i < expectedSizeOfHouseList; i++) {
            this.houses.add(new House(i, 1, expectedNumberOfStones));
        }
        // Store of Player1
        this.houses.add(new House(expectedSizeOfHouseList, 1, 0));

        // Houses of Player2
        for (int i = expectedSizeOfHouseList + 1; i < (expectedSizeOfHouseList * 2) + 1; i++) {
            this.houses.add(new House(i, 2, expectedNumberOfStones));
        }
        // Store of Player2
        this.houses.add(new House((expectedSizeOfHouseList*2)+1, 2, 0));
    }

    public int getHouseIdOfLastHouse() {
        return houses.get(houses.size()-1).getHouseId();
    }

    public int getRemainingStonesOfFirstPlayer(int storeIndexOfFirstPlayer) {
        return houses.stream().filter(house -> house.getOwnedPlayerId().equals(1)
                && !house.getHouseId().equals(storeIndexOfFirstPlayer)).mapToInt(House::getNumberOfStones).sum();
    }

    public int getRemainingStonesOfSecondPlayer(int storeIndexOfSecondPlayer) {
        return houses.stream().filter(house -> house.getOwnedPlayerId().equals(2)
                && !house.getHouseId().equals(storeIndexOfSecondPlayer)).mapToInt(House::getNumberOfStones).sum();
    }



    public ArrayList<House> getHouses() {
        return houses;
    }

    public void setHouses(ArrayList<House> houses) {
        this.houses = houses;
    }


}
