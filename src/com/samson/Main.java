package com.samson;

import com.samson.chess.Board;

import java.awt.*;

public class Main {

    static String[] moves = {
            "e2e4", // white
            "e7e6", // black
            "e1e2", // white
            "f7f6", // black
            "e4e5", // white
            "d7d5", // black
            "e5d6", //white
            "d8e7"
    };

    public static void main(String[] args) {

        System.out.println("Starting game.");

        Board board = new Board();
        System.out.println("Board initialized.");

        board.printBoard();
        System.out.println();

        for(String nMove : moves) {
            board.performMove(nMove);
            board.printBoard();
            System.out.println();
        }
    }
}
