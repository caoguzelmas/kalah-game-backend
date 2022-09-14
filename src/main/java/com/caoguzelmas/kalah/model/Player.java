package com.caoguzelmas.kalah.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Player {

    private Integer playerId;
    private Boolean isActivePlayer;
}
