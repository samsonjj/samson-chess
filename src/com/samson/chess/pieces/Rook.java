package com.samson.chess.pieces;

import com.samson.chess.Board;
import com.samson.chess.Move;
import com.samson.chess.Piece;
import com.samson.chess.Square;

public class Rook extends Piece {
    public Rook(boolean color) {
        super(color, 'R');
    }

    @Override
    public boolean isValidMove(Square fromSquare, Square targetSquare, Board board) {
        if(!super.isValidMove(fromSquare, targetSquare, board)) {
            return false;
        }

        // Make sure it is a square reachable by a rook.
        int dx = targetSquare.getX() - fromSquare.getX();
        int dy = targetSquare.getY() - fromSquare.getY();
        int xDirection = dx == 0 ? 0 : dx / Math.abs(dx);
        int yDirection = dy == 0 ? 0 : dy / Math.abs(dy);
        if(fromSquare.getX() != targetSquare.getX() && fromSquare.getY() != targetSquare.getY()) {
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

    @Override
    public Move performMove(Square fromSquare, Square targetSquare, Board board) {
        return super.performMove(fromSquare, targetSquare, board); // TODO
    }
}
