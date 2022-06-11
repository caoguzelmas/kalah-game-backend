package com.caoguzelmas.kalah.model;

public class GameVariation {

    private Boolean flowsCounterClockwise;
    private Boolean emptyCaptureEnabled;
    private Boolean remainingStonesInsertionEnabled;

    public GameVariation(Boolean flowsCounterClockwise, Boolean emptyCaptureEnabled, Boolean remainingStonesInsertionEnabled) {
        this.flowsCounterClockwise = flowsCounterClockwise;
        this.emptyCaptureEnabled = emptyCaptureEnabled;
        this.remainingStonesInsertionEnabled = remainingStonesInsertionEnabled;
    }

    public GameVariation() {}

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
