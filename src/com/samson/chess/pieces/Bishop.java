package com.samson.chess.pieces;

import com.samson.chess.Board;
import com.samson.chess.Square;

public class Bishop extends Piece {

    public Bishop(boolean color) {
        super(color, 'B');
    }

//    public ArrayList<Move> getMoves(int x, int y, Board board) {
//        ArrayList<Move> moves = new ArrayList<>();
//        for(int i = 1; i <= 7; i++) {
//            for(int j = -1; j <= 1; j+=2) {
//                int xf = x+i, yf = y+i*j;
//                Piece targetPiece = board.getPiece(new Square(xf,yf));
//                Piece fromPiece = board.getPiece(new Square(x, y));
//                if(!Board.isLegalSquare(xf,yf) || (targetPiece != null && targetPiece.color != fromPiece.color)) {
//                    moves.add(new Move(new Square(x,y), new Square(xf,yf), fromPiece, targetPiece));
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

        // Make sure it is a square reachable by a bishop.
        int dx = targetSquare.getX() - fromSquare.getX();
        int dy = targetSquare.getY() - fromSquare.getY();
        int xDirection = dx == 0 ? 0 : dx / Math.abs(dx);
        int yDirection = dy == 0 ? 0 : dy / Math.abs(dy);
        if(Math.abs(dx) != Math.abs(dy)) {
            return false;
        }
        // Make sure there are no pieces in the way
        for(int i = 1; Math.abs(i * xDirection) < Math.abs(dx); i++) {
            Piece intermediatePiece =
                    board.getPiece(fromSquare.getX() + i * xDirection, fromSquare.getY() + i * yDirection);
            if(intermediatePiece != null) {
                return false;
            }
        }

        return true;
    }
}
