package com.caoguzelmas.kalah.model;

public class Game {

    private Integer gameId;
    private Player firstPlayer;
    private Player secondPlayer;
    private Boolean flowsCounterClockwise;
    private Boolean emptyCaptureEnabled;

    public Game(Player firstPlayer, Player secondPlayer, Boolean flowsCounterClockwise, Boolean emptyCaptureEnabled) {
        this.gameId = hashCode();
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.flowsCounterClockwise = flowsCounterClockwise;
        this.emptyCaptureEnabled = emptyCaptureEnabled;
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
}
