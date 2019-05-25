package com.samson;

import com.samson.chess.Board;

import java.awt.*;

public class Main {

    static String[] moves = {
            "e2e4",
            "e7e5"
    };

    public static void main(String[] args) {

        System.out.println("Starting game.");

        Board board = new Board();
        System.out.println("Board initialized.");


        board.printBoard();
        for(String nMove : moves) {
            board.attemptNotationMove(nMove);
            board.printBoard();
            System.out.println();
        }
    }
}
