package com.samson.chess.pieces;

import com.samson.chess.Board;
import com.samson.chess.Square;

public class Queen extends Piece {
    public Queen(boolean color) {
        super(color, 'Q');
    }

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

        // Make sure it is a square reachable by a Queen.
        int dx = targetSquare.getX() - fromSquare.getX();
        int dy = targetSquare.getY() - fromSquare.getY();
        int xDirection = dx == 0 ? 0 : dx / Math.abs(dx);
        int yDirection = dy == 0 ? 0 : dy / Math.abs(dy);
        if(Math.abs(dx) != Math.abs(dy)
                && (fromSquare.getX() != targetSquare.getX() && fromSquare.getY() != targetSquare.getY())) {
            return false;
        }
        // Make sure there are no pieces in the way
        for(int i = 1; Math.abs(i * xDirection) < Math.abs(dx) || Math.abs(i * yDirection) < Math.abs(dy); i++) {
            Piece intermediatePiece =
                    board.getPiece(fromSquare.getX() + i * xDirection, fromSquare.getY() + i * yDirection);
            if(intermediatePiece != null) {
                return false;
            }
        }

        return true;
    }

}
