package com.samson.chess;

import com.samson.chess.pieces.Piece;

public class Move {

    private Square targetSquare;
    private Square fromSquare;
    private Piece fromPiece;
    private Piece targetPiece;
    private boolean enPassant; // false by default
    private Move consequence;

    public Move() {
        targetSquare = null;
        fromSquare = null;
        fromPiece = null;
        targetPiece = null;
        enPassant = false;
        consequence = null;
    }

    public Move(Square fromSquare, Square targetSquare, Piece fromPiece, Piece targetPiece) {
        this.fromSquare = fromSquare;
        this.targetSquare = targetSquare;
        this.fromPiece = fromPiece;
        this.targetPiece = targetPiece;
    }

    public Move(Square from, Square target, Piece fromPiece, Piece targetPiece, boolean enPassant, Move consequence) {
        this(from, target, fromPiece, targetPiece);
        this.enPassant = enPassant;
        this.consequence = consequence;
    }

    public Square targetSquare() {
        return targetSquare;
    }
    public void setTargetSquare(Square targetSquare) {
        this.targetSquare = targetSquare;
    }
    public Square fromSquare() {
        return fromSquare;
    }
    public void setFromSquare(Square fromSquare) {
        this.fromSquare = fromSquare;
    }
    public Piece fromPiece() {
        return fromPiece;
    }
    public void setFromPiece(Piece fromPiece) {
        this.fromPiece = fromPiece;
    }
    public Piece targetPiece() {
        return targetPiece;
    }
    public void setTargetPiece(Piece targetPiece) {
        this.targetPiece = targetPiece;
    }
    public boolean isEnPassant() {
        return enPassant;
    }
    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }
    public Move consequence() {
        return consequence;
    }
    public void setConsequence(Move consequence) {
        this.consequence = consequence;
    }

}
