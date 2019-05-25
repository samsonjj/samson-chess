package com.samson.chess.pieces;

import com.samson.chess.Board;
import com.samson.chess.Move;
import com.samson.chess.Piece;
import com.samson.chess.Square;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(boolean color) {
        super(color);
        this.letter = 'P';
    }

    /*
     *  A pawn can move one square foward. It can move two if it has not moved yet.
     *  It can move diagonally forward 1 square if it is capturing an enemy piece. =
     */
    public ArrayList<Move> getMoves(int x, int y, Board board) {
        ArrayList<Move> moves = new ArrayList<>();

        // Add one square moves.
        Square oneSquareMove = new Square(x, this.color == Piece.WHITE ? y+1 : y-1);
        if(Board.isLegalSquare(oneSquareMove)  && board.getPiece(oneSquareMove) == null){
            moves.add(new Move(new Square(x, y), oneSquareMove, this, null));

            // Add two square moves. This is only allowed if the one square move is legal.
            if(!this.hasMoved(this.color, x, y)) {
                Square twoSquareMove = new Square(x, this.color == Piece.WHITE ? y + 2 : y - 2);
                if(Board.isLegalSquare(twoSquareMove) && board.getPiece(twoSquareMove) == null) {
                    moves.add(new Move(new Square(x, y), twoSquareMove, this, null));
                }
            }
        }

        // Add capturing moves, including enpassant.
        for(int dx = -1; dx <= 1; dx+=2) {
            Square targetSquare = new Square(x+dx, y + (this.color == Piece.WHITE ? 1 : -1));
            if(board.getPiece(targetSquare) != null && board.getPiece(targetSquare).color != this.color) {
                moves.add(new Move(new Square(x,y), targetSquare, this, board.getPiece(targetSquare)));
            }
            else if(board.getEnPassantTargetSquare() == targetSquare) {
                moves.add(new Move(new Square(x,y), targetSquare, this, board.getPiece(board.getEnPassantPieceSquare()), true,
                        new Move(board.getEnPassantPieceSquare(), null, board.getPiece(board.getEnPassantPieceSquare()), null)));
            }
        }

        return moves;
    }

    /*
     *  returns true if the pawn is located on a possible initial square (second rank).
     */
    public static boolean hasMoved(boolean color, int x, int y) {
        return !((color == WHITE && y == 1) || (color == BLACK && y == 6));
    }
}
