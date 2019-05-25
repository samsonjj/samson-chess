package com.samson.chess.pieces;

import com.samson.chess.Board;
import com.samson.chess.Move;
import com.samson.chess.Piece;

import java.awt.*;
import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(boolean color) {
        super(color);
        this.letter = 'B';
    }

    public ArrayList<Move> getMoves(int x, int y, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        for(int i = 1; i <= 7; i++) {
            for(int j = -1; j <= 1; j+=2) {
                int xf = x+i, yf = y+i*j;
                Piece targetPiece = board.getPiece(new Dimension(xf,yf));
                Piece fromPiece = board.getPiece(new Dimension(x, y));
                if(!Board.isLegalSquare(xf,yf) || (targetPiece != null && targetPiece.color != fromPiece.color)) {
                    moves.add(new Move(new Dimension(x,y), new Dimension(xf,yf), fromPiece, targetPiece));
                }
            }
        }
        return moves;
    }

}