package com.samson.chess;

import com.samson.chess.pieces.King;
import com.samson.chess.pieces.Pawn;

import java.awt.*;

public class Board {

    private Piece[][] board;
    private Dimension enPassantTargetSquare;
    private Dimension enPassantPieceSquare;


    public Board() {
        initGamePieces();
    }

    public void initGamePieces() {
        board = new Piece[8][8];
        enPassantTargetSquare = null;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(j == 0) {
                    board[i][j] = new King(Piece.WHITE);
                }
                else if(j == 7) {
                    board[i][j] = new King(Piece.BLACK);
                }
                else if(j == 1) {
                    board[i][j] = new Pawn(Piece.WHITE);
                }
                else if(j == 6) {
                    board[i][j] = new Pawn(Piece.BLACK);
                }
            }
        }
    }

    public boolean attemptMove(Dimension from, Dimension target) {

        // Check legal squares
        if(!isLegalSquare(from) || !isLegalSquare(target)) {
            return false;
        }

        // Check from piece, no ally to piece, or to king
        if(getPiece(from) == null || (getPiece(target) != null && (getPiece(target).getClass() == King.class || getPiece(from).color ==  getPiece(target).color))) {
            return false;
        }

        for(Move possibleMove : getPiece(from).getMoves(from.width, from.height, this)) {
            if(target.width == possibleMove.targetSquare.width && target.height == possibleMove.targetSquare.height) {

                // update board pieces.
                move(possibleMove);

                return true;
            }
        }

        return false;
    }

    public void move(Move move) {
        board[move.fromSquare.width][move.fromSquare.height] = null;
        board[move.targetSquare.width][move.targetSquare.height] = move.fromPiece;

        if(move.consequence != null) {
            move(move.consequence);
        }

        // update enpassant square
        if(move.fromPiece.getClass() == Pawn.class && Math.abs(move.fromSquare.height - move.targetSquare.height) == 2) {
            this.enPassantPieceSquare = move.targetSquare;
            this.enPassantTargetSquare = new Dimension(move.fromSquare.width, (move.targetSquare.height + move.fromSquare.height) / 2);
        }
        else {
            this.enPassantTargetSquare = null;
            this.enPassantPieceSquare = null;
        }
    }

    public void revertMove(Move move) {} // TODO

    public boolean inCheck(boolean color) {
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                if(board[x][y] != null && board[x][y].color != color) {
                    for(Move move: board[x][y].getMoves(x, y, this)) {
                        if(move.toPiece.getClass() == King.class) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isLegalSquare(int x, int y) {
        return x < 8 && x >= 0 && y < 8 && y >= 0;
    }
    public static boolean isLegalSquare(Dimension d) {
        return d.width < 8 && d.width >= 0 && d.height < 8 && d.height >= 0;
    }

    public Piece getPiece(Dimension d) {
        return board[d.width][d.height];
    }

    public Dimension getEnPassantTargetSquare() {
        return enPassantTargetSquare;
    }

    public Dimension getEnPassantPieceSquare() {
        return enPassantPieceSquare;
    }

    public void printBoard() {
        for(int y = 7; y >= 0; y--) {
            for(int x = 0; x < 8; x++) {
                System.out.print("[" + (board[x][y] == null ? '_' : board[x][y].letter) + "]");
            }
            System.out.println();
        }
    }

    public boolean attemptNotationMove(String notation) {

        int xi = "abcdefgh".indexOf(notation.charAt(0));
        int yi = "12345678".indexOf(notation.charAt(1));
        int xf = "abcdefgh".indexOf(notation.charAt(2));
        int yf = "12345678".indexOf(notation.charAt(3));

        if(xi < 0 || yi < 0 || xf < 0 || yf < 0) {
            return false;
        }

        return attemptMove(new Dimension(xi, yi), new Dimension(xf, yf));
    }
}
