package com.samson.chess;

import java.awt.*;
import java.util.ArrayList;

public class Move {

    public Dimension targetSquare;
    public Dimension fromSquare;
    public Piece fromPiece;
    public Piece toPiece;
    public boolean enPassant;
    public Move consequence;

    public Move() {
        targetSquare = null;
        fromSquare = null;
        fromPiece = null;
        toPiece = null;
        enPassant = false;
        consequence = null;
    }

    public Move(Dimension from, Dimension target, Piece fromPiece, Piece toPiece) {
        this(from, target, fromPiece, toPiece, false, null);
    }

    public Move(Dimension from, Dimension target, Piece fromPiece, Piece toPiece, boolean enPassant, Move consequence) {
        this.fromSquare = from;
        this.targetSquare = target;
        this.fromPiece = fromPiece;
        this.toPiece = toPiece;
        this.enPassant = enPassant;
        this.consequence = consequence;
    }
}
