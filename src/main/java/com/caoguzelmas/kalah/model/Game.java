package com.caoguzelmas.kalah.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Game extends ResponseCode {

    private Integer gameId;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameBoard gameBoard;
    private Integer winnerPlayerId;
    private Boolean flowsCounterClockwise;
    private Boolean emptyCaptureEnabled;
    private Boolean remainingStonesInsertionEnabled;


    public Game(Player firstPlayer, Player secondPlayer, int numberOfHouses, int numberOfStones,
                Boolean flowsCounterClockwise, Boolean emptyCaptureEnabled, Boolean remainingStonesInsertionEnabled,
                ResponseCode responseCode) {
        super(responseCode.isSuccess(), responseCode.getMessage());
        this.gameId = hashCode();
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.gameBoard = new GameBoard(numberOfHouses, numberOfStones);
        this.flowsCounterClockwise = flowsCounterClockwise;
        this.emptyCaptureEnabled = emptyCaptureEnabled;
        this.remainingStonesInsertionEnabled = remainingStonesInsertionEnabled;
    }

    public Game() {
        super();
    }

    @JsonIgnore
    public Player getActivePlayer() {
        return getFirstPlayer().getActivePlayer() ? getFirstPlayer() : getSecondPlayer();
    }

    @JsonIgnore
    public Player getInactivePlayer() {
        return !getFirstPlayer().getActivePlayer() ? getFirstPlayer() : getSecondPlayer();
    }

    @JsonIgnore
    public int getStoreIndexOfActivePlayer() {
        return getActivePlayer().getPlayerId().equals(1) ? getStoreIndexOfFirstPlayer() : getStoreIndexOfSecondPlayer();
    }

    @JsonIgnore
    public int getStoreIndexOfInactivePlayer() {
        return getInactivePlayer().getPlayerId().equals(1) ? getStoreIndexOfSecondPlayer() : getStoreIndexOfFirstPlayer();
    }

    @JsonIgnore
    public int getStoreIndexOfFirstPlayer() {
        return (getGameBoard().getHouses().size() - 2) / 2;
    }

    @JsonIgnore
    public int getStoreIndexOfSecondPlayer() {
        return getGameBoard().getHouses().size() - 1;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
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

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Integer getWinnerPlayerId() {
        return winnerPlayerId;
    }

    public void setWinnerPlayerId(Integer winnerPlayerId) {
        this.winnerPlayerId = winnerPlayerId;
    }

    public Boolean getFlowsCounterClockwise() {
        return flowsCounterClockwise;
    }

    public void setFlowsCounterClockwise(Boolean flowsCounterClockwise) {
        this.flowsCounterClockwise = flowsCounterClockwise;
    }

    public Boolean getEmptyCaptureEnabled() {
        return emptyCaptureEnabled;
    }

    public void setEmptyCaptureEnabled(Boolean emptyCaptureEnabled) {
        this.emptyCaptureEnabled = emptyCaptureEnabled;
    }

    public Boolean getRemainingStonesInsertionEnabled() {
        return remainingStonesInsertionEnabled;
    }

    public void setRemainingStonesInsertionEnabled(Boolean remainingStonesInsertionEnabled) {
        this.remainingStonesInsertionEnabled = remainingStonesInsertionEnabled;
    }
}
