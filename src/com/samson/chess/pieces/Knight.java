package com.samson.chess.pieces;

import com.samson.chess.Board;
import com.samson.chess.Move;
import com.samson.chess.Piece;
import com.samson.chess.Square;

import java.awt.*;
import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(boolean color) {
        super(color, 'N');
    }

    @Override
    public boolean isValidMove(Square fromSquare, Square targetSquare, Board board) {
        if(!super.isValidMove(fromSquare, targetSquare, board)) {
            return false;
        }

        // Make sure it is a square reachable by a knight.
        int dx = targetSquare.getX() - fromSquare.getX();
        int dy = targetSquare.getY() - fromSquare.getY();
        if(Math.abs(dx) + Math.abs(dy) != 3
                || targetSquare.getX() == fromSquare.getX()
                || targetSquare.getY() == fromSquare.getY()) {
            return false;
        }

        return true;
    }

    @Override
    public Move performMove(Square fromSquare, Square targetSquare, Board board) {
        return super.performMove(fromSquare, targetSquare, board); // TODO
    }

}

