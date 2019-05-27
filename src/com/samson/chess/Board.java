package com.samson.chess;

import com.samson.chess.pieces.*;

import java.util.ArrayList;

public class Board {

    private Piece[][] board;
    private Square enPassantTargetSquare;
    private Square enPassantPieceSquare;
    private boolean turn;

    private ArrayList<BoardChange> boardChanges = new ArrayList<>();

    public static final String[][] initialBoard = {
            {"Rb", "Nb", "Bb", "Qb", "Kb", "Bb", "Nb", "Rb"},
            {"Pb", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb"},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"Pw", "Pw", "Pw", "Pw", "Pw", "Pw", "Pw", "Pw"},
            {"Rw", "Nw", "Bw", "Qw", "Kw", "Bw", "Nw", "Rw"}
    };

    static class BoardChange {
        ArrayList<SquareChange> squareChanges = new ArrayList<>();
    }

    static class SquareChange {
        Square square;
        Piece before;
        Piece after;

        public SquareChange(Square square, Piece before, Piece after) {
            this.square = square;
            this.before = before;
            this.after = after;
        }
    }

    public Board() {
        initGamePieces();
        turn = Piece.WHITE;
    }

    public void initGamePieces() {
        board = new Piece[8][8];
        enPassantTargetSquare = null;
        for(int i = 0; i < initialBoard.length; i++) {
            for(int j = 0; j < initialBoard[i].length; j++) {
                String s = initialBoard[i][j];
                char pieceCharacter = s.charAt(0);
                char colorCharacter = s.charAt(1);

                boolean color = colorCharacter == 'w' ? Piece.WHITE : Piece.BLACK;

                if(pieceCharacter == ' ') {
                    continue;
                }
                switch(pieceCharacter) {
                    case 'K':
                        board[j][7-i] = new King(color);
                        break;
                    case 'P':
                        board[j][7-i] = new Pawn(color);
                        break;
                    case 'B':
                        board[j][7-i] = new Bishop(color);
                        break;
                    case 'N':
                        board[j][7-i] = new Knight(color);
                        break;
                    case 'R':
                        board[j][7-i] = new Rook(color);
                        break;
                    case 'Q':
                        board[j][7-i] = new Queen(color);
                        break;
                }
            }
        }
    }

    public boolean isLegalMove(Square fromSquare, Square targetSquare) {
        if(getPiece(fromSquare) == null) {
            return false;
        }
        return getPiece(fromSquare).isValidMove(fromSquare, targetSquare, this);
    }

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
        Square kingSquare = null;
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(getPiece(i,j) != null && getPiece(i,j) instanceof King && getPiece(i,j).color == color) {
                    kingSquare = new Square(i,j);
                    for(int x = 0; x < 8; x++) {
                        for(int y = 0; y < 8; y++) {
                            if(board[x][y] != null && board[x][y].color != color) {
                                if (getPiece(kingSquare).isValidMove(new Square(x, y), kingSquare, this)) {
                                    return true;
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return false;
    }

    public static boolean isLegalSquare(int x, int y) {
        return x < 8 && x >= 0 && y < 8 && y >= 0;
    }
    public static boolean isLegalSquare(Square square) {
        return square.getX() < 8 && square.getX() >= 0 && square.getY() < 8 && square.getY() >= 0;
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

    public Piece getPiece(Square square) {
        return getPiece(square.getX(), square.getY());
    }
    public Piece getPiece(int x, int y) {
        return board[x][y];
    }

    public Square getEnPassantTargetSquare() {
        return enPassantTargetSquare;
    }
    public Square getEnPassantPieceSquare() {
        return enPassantPieceSquare;
    }
    public void setEnPassantTargetSquare(Square square) {
        enPassantTargetSquare = square;
    }
    public void setEnPassantPieceSquare(Square square) {
        enPassantPieceSquare = square;
    }

    public boolean getTurn() { return turn; }
}
