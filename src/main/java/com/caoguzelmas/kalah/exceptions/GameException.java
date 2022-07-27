package com.caoguzelmas.kalah.exceptions;

public class GameException extends RuntimeException  {

    public GameException(String errorMessage) {
        super("Game Exception: " + errorMessage);
    }
}
