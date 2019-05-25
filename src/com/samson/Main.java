package com.samson;

import com.samson.chess.Board;

import java.awt.*;

public class Main {

    static String[] moves = {
            "e1e2",
            "e8e7"
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
