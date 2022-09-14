package com.caoguzelmas.kalah.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Game {

    @Id
    private String gameId;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameBoard gameBoard;
    private Integer winnerPlayerId;
    private GameVariation gameVariation;
    private Date createDate;

    public Game(Player firstPlayer, Player secondPlayer, int numberOfHouses, int numberOfStones,
                Boolean flowsCounterClockwise, Boolean emptyCaptureEnabled, Boolean remainingStonesInsertionEnabled) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.gameBoard = new GameBoard(numberOfHouses, numberOfStones);
        this.gameVariation = new GameVariation(flowsCounterClockwise, emptyCaptureEnabled, remainingStonesInsertionEnabled);
        this.createDate = new Date();
    }

    @JsonIgnore
    public Player getActivePlayer() {
        return getFirstPlayer().getIsActivePlayer() ? getFirstPlayer() : getSecondPlayer();
    }

    @JsonIgnore
    public Player getInactivePlayer() {
        return !getFirstPlayer().getIsActivePlayer() ? getFirstPlayer() : getSecondPlayer();
    }

    @JsonIgnore
    public int getStoreIndexOfActivePlayer() {
        return getActivePlayer().getPlayerId().equals(1) ? getStoreIndexOfFirstPlayer() : getStoreIndexOfSecondPlayer();
    }

    @JsonIgnore
    public int getStoreIndexOfInactivePlayer() {
        return getInactivePlayer().getPlayerId().equals(1) ? getStoreIndexOfFirstPlayer(): getStoreIndexOfSecondPlayer();
    }

    @JsonIgnore
    public int getStoreIndexOfFirstPlayer() {
        return (getGameBoard().getHouses().size() - 2) / 2;
    }

    @JsonIgnore
    public int getStoreIndexOfSecondPlayer() {
        return getGameBoard().getHouses().size() - 1;
    }
}
