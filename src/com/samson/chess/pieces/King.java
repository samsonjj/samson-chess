package com.samson.chess.pieces;

import com.samson.chess.*;

public class King extends Piece {

    public King(boolean color) {
        super(color, 'K');
    }

//    @Override
//    public ArrayList<Move> getMoves(int x, int y, Board board) {
//        ArrayList<Move> moves = new ArrayList<>();
//        for (int i = -1; i <= 1; i++) {
//            for (int j = -1; j <= 1; j++) {
//                if ((i != 0 || j != 0) && Board.isLegalSquare(x + i, y + i)) {
//                    moves.add(new Move(new Square(x, y), new Square(x + i, y + i), this, board.getPiece(new Square(x + i, y + i))));
//                }
//            }
//        }
//        return moves;
//    }

    @Override
    public boolean isValidMove(Square fromSquare, Square targetSquare, Board board) {
        if(!super.isValidMove(fromSquare, targetSquare, board)) {
            return false;
        }
        Board.Move move = performMove(fromSquare, targetSquare, board);
        if(board.wouldBeInCheck(move)) {
            return false;
        }

        return attacksSquare(fromSquare, targetSquare, board);
    }

    @Override
    public Board.Move performMove(Square fromSquare, Square targetSquare, Board board) {
        return super.performMove(fromSquare, targetSquare, board);
    }

    @Override
    public boolean attacksSquare(Square fromSquare, Square targetSquare, Board board) {
        if(!super.attacksSquare(fromSquare, targetSquare, board)) {
            return false;
        }

        // Make sure it is a square reachable by a king.
        if(Math.abs(targetSquare.getX() - fromSquare.getX()) > 1
                || Math.abs(targetSquare.getY() - fromSquare.getY()) > 1
                || (targetSquare.getX() - fromSquare.getX() == 0 && targetSquare.getY() - fromSquare.getY() == 0))
        {
            return false;
        }

        return true;
    }
}