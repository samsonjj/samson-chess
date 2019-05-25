package com.samson.chess;

public class IllegalChessMoveException extends IllegalArgumentException {
    public IllegalChessMoveException() {
        super("A move was performed which was not legal on the given board.");
    }
    public IllegalChessMoveException(String message) {
        super(message);
    }
}
