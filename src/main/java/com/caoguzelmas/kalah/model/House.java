package com.caoguzelmas.kalah.model;

public class House {

    private Integer houseId;
    private Integer numberOfStones;

    public House(Integer houseId, Integer numberOfStones) {
        this.houseId = houseId;
        this.numberOfStones = numberOfStones;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getNumberOfStones() {
        return numberOfStones;
    }

    public void setNumberOfStones(Integer numberOfStones) {
        this.numberOfStones = numberOfStones;
    }
}
