package com.samson.chess.pieces;

import com.samson.chess.Board;
import com.samson.chess.Move;
import com.samson.chess.Piece;

import java.awt.*;
import java.util.ArrayList;

public class King extends Piece {

    public King(boolean color) {
        super(color);
        this.letter = 'K';
    }

    @Override
    public ArrayList<Move> getMoves(int x, int y, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                if((i != 0 || j != 0) && Board.isLegalSquare(x+i, y+i)) {
                    moves.add(new Move(new Dimension(x,y), new Dimension(x+i, y+i), this, board.getPiece(new Dimension(x+i, y+i))));
                }
            }
        }
        return moves;
    }
}
