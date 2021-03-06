package com.samson;

import com.samson.chess.Board;
import com.samson.chess.Display.GameFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {

    static String[] moves = {
            "e2e4", // white
            "e7e6", // black
    };

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting game.");
        System.out.println(System.getProperty("user.dir"));


//        Board board = new Board();
//        System.out.println("Board initialized.");
//
//        board.printBoard();
//        System.out.println();
//
//        for(String nMove : moves) {
//            board.getMove(nMove);
//            board.printBoard();
//            System.out.println();
//        }

        Scanner in = new Scanner(System.in);

        GameFrame frame = new GameFrame();
        while(true) {
            frame.getBoard().performMove(in.nextLine());
            frame.repaint();
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