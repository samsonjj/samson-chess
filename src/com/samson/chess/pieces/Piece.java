package com.samson.chess.pieces;

import com.samson.chess.Board;
import com.samson.chess.IllegalChessMoveException;
import com.samson.chess.Square;

import java.util.ArrayList;

/*
 *  Objects inheriting Piece have a responsibility to determine target squares given the board,
 *  taking into account the location of other pieces. This responsibility is not held by the board class.
 */
public abstract class Piece {

    public static final boolean WHITE = true;
    public static final boolean BLACK = false;
    public final char letter;
    public final boolean color;

    public Piece(boolean color) {
        this.color = color;
        this.letter = 'x';
    }
    public Piece(boolean color, char letter) {
        this.color = color;
        this.letter = letter;
    }

    // public abstract ArrayList<Board.Move> getMoves(int x, int y, Board board);

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

    public Board.Move performMove(Square fromSquare, Square targetSquare, Board board) {

        // save target piece in variable, since it may change during move.
        Piece targetPiece = board.getPiece(targetSquare);

        Board.Move changes = new Board.Move();

        changes.add(new Board.SquareChange(fromSquare, board.getPiece(fromSquare), null));
        changes.add(new Board.SquareChange(targetSquare, board.getPiece(targetSquare), this));
        changes.beforeEnPassantPieceSquare = board.getEnPassantPieceSquare();
        changes.beforeEnPassantTargetSquare = board.getEnPassantTargetSquare();
        changes.afterEnPassantPieceSquare = null;
        changes.afterEnPassantPieceSquare = null;
        changes.color = this.color;

        return changes;
    }

    public boolean attacksSquare(Square fromSquare, Square targetSquare, Board board) {
        if(!Board.isLegalSquare(fromSquare) || !Board.isLegalSquare(targetSquare)) {
            return false;
        }
        if(board.getPiece(fromSquare) != this) {
            return false;
        }
        return true;
    }
}
