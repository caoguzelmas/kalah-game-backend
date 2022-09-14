package com.caoguzelmas.kalah.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRequestDto {

    private Integer numberOfHouses;
    private Integer numberOfStones;
    private Boolean flowsCounterClockwise;
    private Boolean firstMoveOnFirstPlayer;
    private Boolean emptyCaptureEnabled;
    private Boolean remainingStonesInsertionEnabled;
}
