package com.caoguzelmas.kalah.exceptions;

public class IllegalMoveException extends RuntimeException {

    public IllegalMoveException(String errorMessage) {
        super("Illegal Move: " + errorMessage);
    }
}
