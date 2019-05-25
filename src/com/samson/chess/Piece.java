package com.samson.chess;

import com.samson.chess.pieces.King;

import java.awt.*;
import java.util.ArrayList;

/*
 *  Objects inheriting Piece have a responsibility to determine target squares given the board,
 *  taking into account the location of other pieces. This responsibility is not held by the board class.
 */
public abstract class Piece {

    public static final boolean WHITE = true;
    public static final boolean BLACK = false;
    public final char letter;
    public boolean color;

    public Piece(boolean color) {
        this.color = color;
        this.letter = 'x';
    }
    public Piece(boolean color, char letter) {
        this.color = color;
        this.letter = letter;
    }

//    public abstract ArrayList<Move> getMoves(int x, int y, Board board);

    public boolean isValidMove(Square fromSquare, Square targetSquare, Board board) {
        if(!Board.isLegalSquare(fromSquare) || !Board.isLegalSquare(targetSquare)) {
            return false;
        }
        if(board.getTurn() != this.color) {
            return false;
        }
        if(board.getPiece(fromSquare) != this) {
            return false;
        }
        if(board.getPiece(targetSquare) != null && board.getPiece(targetSquare).color == this.color) {
            return false;
        }
        if(board.getPiece(targetSquare) instanceof King) {
            return false;
        }
        return true;
    }

    public Move performMove(Square fromSquare, Square targetSquare, Board board) {
        if(!isValidMove(fromSquare, targetSquare, board)) {
            throw new IllegalChessMoveException();
        }

        // save target piece in variable, since it may change during move.
        Piece targetPiece = board.getPiece(targetSquare);

        Piece[][] boardPieces = board.getBoard();
        boardPieces[fromSquare.getX()][fromSquare.getY()] = null;
        boardPieces[targetSquare.getX()][targetSquare.getY()] = this;

        return new Move(fromSquare, targetSquare, board.getPiece(fromSquare), targetPiece);
    }
}
