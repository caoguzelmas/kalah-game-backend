package com.caoguzelmas.kalah.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class House {

    private Integer houseId;
    private Integer ownedPlayerId;
    private Integer numberOfStones;

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
