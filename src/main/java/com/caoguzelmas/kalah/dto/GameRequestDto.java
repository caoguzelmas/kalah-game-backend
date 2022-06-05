package com.caoguzelmas.kalah.dto;

public class GameRequestDto {

    private Integer numberOfHouses;
    private Integer numberOfStones;
    private Boolean flowsCounterClockwise;
    private Boolean firstMoveOnFirstPlayer;
    private Boolean emptyCaptureEnabled;

    public Integer getNumberOfHouses() {
        return numberOfHouses;
    }

    public void setNumberOfHouses(Integer numberOfHouses) {
        this.numberOfHouses = numberOfHouses;
    }

    public Integer getNumberOfStones() {
        return numberOfStones;
    }

    public void setNumberOfStones(Integer numberOfStones) {
        this.numberOfStones = numberOfStones;
    }

    public Boolean getFlowsCounterClockwise() {
        return flowsCounterClockwise;
    }

    public void setFlowsCounterClockwise(Boolean flowsCounterClockwise) {
        this.flowsCounterClockwise = flowsCounterClockwise;
    }

    public Boolean getFirstMoveOnFirstPlayer() {
        return firstMoveOnFirstPlayer;
    }

    public void setFirstMoveOnFirstPlayer(Boolean firstMoveOnFirstPlayer) {
        this.firstMoveOnFirstPlayer = firstMoveOnFirstPlayer;
    }

    public Boolean getEmptyCaptureEnabled() {
        return emptyCaptureEnabled;
    }

    public void setEmptyCaptureEnabled(Boolean emptyCaptureEnabled) {
        this.emptyCaptureEnabled = emptyCaptureEnabled;
    }
}
