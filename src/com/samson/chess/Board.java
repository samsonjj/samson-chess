package com.samson.chess;

import com.samson.chess.pieces.King;
import com.samson.chess.pieces.Pawn;

import java.awt.*;

public class Board {

    private Piece[][] board;
    private Square enPassantTargetSquare;
    private Square enPassantPieceSquare;
    private boolean turn;

    public Board() {
        initGamePieces();
        turn = Piece.WHITE;
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
                    //board[i][j] = new Pawn(Piece.WHITE);
                }
                else if(j == 6) {
                    //board[i][j] = new Pawn(Piece.BLACK);
                }
            }
        }
    }

//    public boolean attemptMove(Square from, Square target) {
//
//        // Check if the squares exist on the board.
//        if(!isLegalSquare(from) || !isLegalSquare(target)) {
//            return false;
//        }
//
//        for(Move possibleMove : getPiece(from).getMoves(from.x, from.y, this)) {
//            if(target.x == possibleMove.targetSquare().x && target.y == possibleMove.targetSquare().y) {
//
//                // update board pieces.
//                move(possibleMove);
//
//                return true;
//            }
//        }
//
//        return false;
//    }

//    public boolean isValidMove(Square from, Square target) {
//
//        // Check if the squares exist on the board.
//        if(!isLegalSquare(from) || !isLegalSquare(target)) {
//            return false;
//        }
//
//        for(Move possibleMove : getPiece(from).getMoves(from.x, from.y, this)) {
//            if(target.x == possibleMove.targetSquare().x && target.y == possibleMove.targetSquare().y) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    public boolean isValidMove(Square fromSquare, Square targetSquare) {
        if(getPiece(fromSquare) == null) {
            return false;
        }
        return getPiece(fromSquare).isValidMove(fromSquare, targetSquare, this);
    }

//    private void move(Move move) {
//        board[move.fromSquare().x][move.fromSquare().y] = null;
//        board[move.targetSquare().x][move.targetSquare().y] = move.fromPiece();
//
//        if(move.consequence() != null) {
//            move(move.consequence());
//        }
//
//        // update enpassant square
//        if(move.fromPiece().getClass() == Pawn.class && Math.abs(move.fromSquare().y - move.targetSquare().y) == 2) {
//            this.enPassantPieceSquare = move.targetSquare();
//            this.enPassantTargetSquare = new Square(move.fromSquare().x, (move.targetSquare().y + move.fromSquare().y) / 2);
//        }
//        else {
//            this.enPassantTargetSquare = null;
//            this.enPassantPieceSquare = null;
//        }
//    }

    public Move performMove(Square fromSquare, Square targetSquare) {
        if(getPiece(fromSquare) == null) {
            throw new IllegalChessMoveException("No piece was found on the given fromSquare on the board.");
        }
        Move moveResult = getPiece(fromSquare).performMove(fromSquare, targetSquare, this);
        this.turn = !this.turn;
        return moveResult;
    }

    public Move performMove(String notation) {

        int xi = Square.fileToInt(notation.charAt(0));
        int yi = Square.rankToInt(notation.charAt(1));
        int xf = Square.fileToInt(notation.charAt(2));
        int yf = Square.rankToInt(notation.charAt(3));

        return performMove(new Square(xi, yi), new Square(xf, yf));
    }

    public void revertMove(Move move) {} // TODO

    public boolean inCheck(boolean color) {
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                if(board[x][y] != null && board[x][y].color != color) {
                    for(Move move: board[x][y].getMoves(x, y, this)) {
                        if(move.targetPiece().getClass() == King.class) {
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
    public static boolean isLegalSquare(Square d) {
        return d.x < 8 && d.x >= 0 && d.y < 8 && d.y >= 0;
    }

    public void printBoard() {
        for(int y = 7; y >= 0; y--) {
            for(int x = 0; x < 8; x++) {
                System.out.print("[" + (board[x][y] == null ? '_' : board[x][y].letter) + "]");
            }
            System.out.println();
        }
    }


    /* Getters and Setters */

    public Piece[][] getBoard() { return board; }

    public Piece getPiece(Square d) {
        return board[d.x][d.y];
    }

    public Square getEnPassantTargetSquare() {
        return enPassantTargetSquare;
    }

    public Square getEnPassantPieceSquare() {
        return enPassantPieceSquare;
    }

    public boolean getTurn() { return turn; }
}
