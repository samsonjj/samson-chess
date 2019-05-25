package com.samson;

import com.samson.chess.Board;

import java.awt.*;

public class Main {

    static String[] moves = {
            "e2e4", // white
            "e7e6", // black
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

//    public static final String[][] initialBoard = {
//            {"Kb", "Nb", "Kb", "Bb", "Bb", "Bb", "Kb", "Kb"},
//            {"Pb", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb"},
//            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "Qb"},
//            {"Rb", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
//            {"Rw", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
//            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "Qw"},
//            {"Pw", "Pw", "Pw", "Pw", "Pw", "Pw", "Pw", "Pw"},
//            {"Kw", "Nw", "Kw", "Kw", "Kw", "Kw", "Kw", "Kw"}
//    };
//    static String[] moves = {
//            "e2e4", // white
//            "e7e6", // black
//            "e1e2", // white
//            "f7f6", // black
//            "e4e5", // white
//            "d7d5", // black
//            "e5d6", // white
//            "d8e7", // black
//            "b1c3", // white
//            "a5h5", // black
//            "a4a7", // white
//            "h6g6", // black
//            "h3h5", // white
//            "g6c2"  // black
//
//    };