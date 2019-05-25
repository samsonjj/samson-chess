package com.samson.chess;

import java.awt.*;
import java.util.ArrayList;

/*
 *  Objects inheriting Piece have a responsibility to determine target squares given the board,
 *  taking into account the location of other pieces. This responsibility is not held by the board class.
 */
public abstract class Piece {

    public static final boolean WHITE = true;
    public static final boolean BLACK = false;
    public char letter = 'x';

    public Piece(boolean color) {
        this.color = color;
    }

    public boolean color;
    public abstract ArrayList<Move> getMoves(int x, int y, Board board);
}
