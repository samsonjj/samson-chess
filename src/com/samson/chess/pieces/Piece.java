package com.samson.chess.pieces;

import com.samson.chess.Board;
import com.samson.chess.IllegalChessMoveException;
import com.samson.chess.Square;

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

    public Board.BoardChange performMove(Square fromSquare, Square targetSquare, Board board) {
        if(!isValidMove(fromSquare, targetSquare, board)) {
            throw new IllegalChessMoveException();
        }

        // save target piece in variable, since it may change during move.
        Piece targetPiece = board.getPiece(targetSquare);

        Board.BoardChange changes = new Board.BoardChange();

        changes.add(new Board.SquareChange(fromSquare, board.getPiece(fromSquare), null));
        changes.add(new Board.SquareChange(targetSquare, board.getPiece(targetSquare), this));

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
