package com.samson.chess;

import com.samson.chess.pieces.*;

import java.util.ArrayList;

public class Board {

    private Piece[][] board;
    private Square enPassantTargetSquare;
    private Square enPassantPieceSquare;
    private boolean turn;

    private ArrayList<Move> boardChanges = new ArrayList<>();

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

    public static class Move {
        ArrayList<SquareChange> squareChanges = new ArrayList<>();
        public boolean color;
        public Square beforeEnPassantTargetSquare;
        public Square afterEnPassantTargetSquare;
        public Square beforeEnPassantPieceSquare;
        public Square afterEnPassantPieceSquare;
        
        public Move() { }
        public void add(SquareChange change) { squareChanges.add(change); }
        public ArrayList<SquareChange> getSquareChanges() { return squareChanges; }
    }

    public static class SquareChange {
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

    public void performMove(Square fromSquare, Square targetSquare) {
        if(getPiece(fromSquare) == null) {
            throw new IllegalChessMoveException("No piece was found on the given fromSquare on the board.");
        }
        if(!isLegalMove(fromSquare, targetSquare)) {
            throw new IllegalChessMoveException("Move given was illegal.");
        }
        Move changes = getPiece(fromSquare).performMove(fromSquare, targetSquare, this);
        for(SquareChange change : changes.getSquareChanges()) {
            this.board[change.square.getX()][change.square.getY()] = change.after;
        }
        enPassantPieceSquare = changes.afterEnPassantPieceSquare;
        enPassantTargetSquare = changes.afterEnPassantTargetSquare;


        this.turn = !this.turn;
    }
    public boolean attemptMove(Square fromSquare, Square targetSquare) {
        try {
            performMove(fromSquare, targetSquare);
        }
        catch(IllegalChessMoveException e) {
            return false;
        }
        return true;
    }

    public void performMove(String notation) {

        int xi = Square.fileToInt(notation.charAt(0));
        int yi = Square.rankToInt(notation.charAt(1));
        int xf = Square.fileToInt(notation.charAt(2));
        int yf = Square.rankToInt(notation.charAt(3));

        performMove(new Square(xi, yi), new Square(xf, yf));
    }

    public void revertMove(Move changes) {
        for(SquareChange change : changes.getSquareChanges()) {
            board[change.square.getX()][change.square.getY()] = change.before;
        }
        enPassantPieceSquare = changes.beforeEnPassantPieceSquare;
        enPassantTargetSquare = changes.afterEnPassantTargetSquare;
    }

    public boolean inCheck(boolean color) {
        Square kingSquare = null;
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(getPiece(i,j) != null && getPiece(i,j) instanceof King && getPiece(i,j).color == color) {
                    kingSquare = new Square(i,j);
                    for(int x = 0; x < 8; x++) {
                        for(int y = 0; y < 8; y++) {
//                            if(board[x][y] != null && board[x][y].color != color) {
//                                if (getPiece(kingSquare).isValidMove(new Square(x, y), kingSquare, this)) {
//                                    return true;
//                                }
//                            }
                            if(getPiece(x, y) != null
                                    && getPiece(x, y).color != color
                                    && getPiece(x, y).attacksSquare(new Square(x, y), kingSquare, this)) {
                                return true;
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

    public boolean getTurn() { return turn; }

//    public ArrayList<Move> getAllLegalMoves() {
//        ArrayList<Move> moveList = new ArrayList<>();
//        for(int i = 0; i < board.length; i++) {
//            for(int j = 0; j < board[i].length; j++) {
//                if(board[i][j] != null) {
//                    for(Move move : board[i][j].getMoves()) {
//                        moveList.add(move);
//                    }
//                }
//            }
//        }
//        return moveList;
//    }

    public boolean wouldBeInCheck(Move move) {

        // perform move
        for(SquareChange change : move.getSquareChanges()) {
            this.board[change.square.getX()][change.square.getY()] = change.after;
        }
        enPassantPieceSquare = move.afterEnPassantPieceSquare;
        enPassantTargetSquare = move.afterEnPassantTargetSquare;

        // look for check
        boolean inCheck = inCheck(move.color);

        // revert move
        revertMove(move);

        return inCheck;
    }
}
