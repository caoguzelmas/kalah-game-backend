package com.caoguzelmas.kalah.model;

public class House {

    private Integer houseId;
    private Integer ownedPlayerId;
    private Integer numberOfStones;

    public House(Integer houseId, Integer ownedPlayerId, Integer numberOfStones) {
        this.houseId = houseId;
        this.ownedPlayerId = ownedPlayerId;
        this.numberOfStones = numberOfStones;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getOwnedPlayerId() {
        return ownedPlayerId;
    }

    public void setOwnedPlayerId(Integer ownedPlayerId) {
        this.ownedPlayerId = ownedPlayerId;
    }

    public Integer getNumberOfStones() {
        return numberOfStones;
    }

    public void setNumberOfStones(Integer numberOfStones) {
        this.numberOfStones = numberOfStones;
    }

    public void addOne() {
        this.numberOfStones += 1;
    }

    public void removeOne() {
        this.numberOfStones -= 1;
    }

    public void addStones(int numberOfStones) {
        this.numberOfStones += numberOfStones;
    }

    public void removeStones(int numberOfStones) {
        this.numberOfStones -= numberOfStones;
    }
}
