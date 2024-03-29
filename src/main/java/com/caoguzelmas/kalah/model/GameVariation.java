package com.caoguzelmas.kalah.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameVariation {

    private Boolean flowsCounterClockwise;
    private Boolean emptyCaptureEnabled;
    private Boolean remainingStonesInsertionEnabled;
}
