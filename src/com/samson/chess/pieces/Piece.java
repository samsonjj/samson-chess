package com.samson.chess.pieces;

import com.samson.chess.Board;
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

    public Board.Move getMove(Square fromSquare, Square targetSquare, Board board) {

        // save target piece in variable, since it may change during move.
        Piece targetPiece = board.getPiece(targetSquare);

        Board.Move move = new Board.Move(fromSquare, targetSquare);

        move.add(new Board.SquareChange(fromSquare, board.getPiece(fromSquare), null));
        move.add(new Board.SquareChange(targetSquare, board.getPiece(targetSquare), this));
        move.beforeEnPassantPieceSquare = board.getEnPassantPieceSquare();
        move.beforeEnPassantTargetSquare = board.getEnPassantTargetSquare();
        move.afterEnPassantPieceSquare = null;
        move.afterEnPassantPieceSquare = null;
        move.color = this.color;

        return move;
    }

    // returns true if the piece on 'fromSquare' from 'board' can capture on 'targetSquare' if
    // there were a pawn (or some other arbitrary non-king piece) on 'targetSquare', and ignoring
    // all other pieces on the board
    public boolean attacksSquare(Square fromSquare, Square targetSquare, Board board) {
        if(!Board.isLegalSquare(fromSquare) || !Board.isLegalSquare(targetSquare)) {
            return false;
        }
        if(board.getPiece(fromSquare) != this) {
            return false;
        }
        return true;
    }

    public ArrayList<Board.Move> getAllLegalMoves(Square fromSquare, Board board) {
        ArrayList<Square> targetSquares = targetSquareList(fromSquare);
        ArrayList<Board.Move> legalMoves = new ArrayList<>();
        for(Square targetSquare: targetSquares) {
            if (board.isLegalMove(fromSquare, targetSquare)) {
                legalMoves.add(this.getMove(fromSquare, targetSquare, board));
            }
        }
        return legalMoves;
    }

    public abstract ArrayList<Square> targetSquareList(Square fromSquare);
}
