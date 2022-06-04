package com.caoguzelmas.kalah.model;

public class Game {

    private Integer gameId;
    private Player firstPlayer;
    private Player secondPlayer;
    private Boolean flowsCounterClockwise;

    public Game(Player firstPlayer, Player secondPlayer, Boolean flowsCounterClockwise) {
        this.gameId = hashCode();
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.flowsCounterClockwise = flowsCounterClockwise;
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
}
