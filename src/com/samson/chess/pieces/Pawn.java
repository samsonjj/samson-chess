package com.samson.chess.pieces;

import com.samson.chess.*;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(boolean color) {
        super(color, 'P');
    }

    /*
     *  A pawn can move one square foward. It can move two if it has not moved yet.
     *  It can move diagonally forward 1 square if it is capturing an enemy piece. =
     */
//    @Override
////    public ArrayList<Move> getMoves(int x, int y, Board board) {
////        ArrayList<Move> moves = new ArrayList<>();
////
////        // Add one square moves.
////        Square oneSquareMove = new Square(x, this.color == Piece.WHITE ? y+1 : y-1);
////        if(Board.isLegalSquare(oneSquareMove)  && board.getPiece(oneSquareMove) == null){
////            moves.add(new Move(new Square(x, y), oneSquareMove, this, null));
////
////            // Add two square moves. This is only allowed if the one square move is legal.
////            if(!this.hasMoved(this.color, x, y)) {
////                Square twoSquareMove = new Square(x, this.color == Piece.WHITE ? y + 2 : y - 2);
////                if(Board.isLegalSquare(twoSquareMove) && board.getPiece(twoSquareMove) == null) {
////                    moves.add(new Move(new Square(x, y), twoSquareMove, this, null));
////                }
////            }
////        }
////
////        // Add capturing moves, including enpassant.
////        for(int dx = -1; dx <= 1; dx+=2) {
////            Square targetSquare = new Square(x+dx, y + (this.color == Piece.WHITE ? 1 : -1));
////            if(board.getPiece(targetSquare) != null && board.getPiece(targetSquare).color != this.color) {
////                moves.add(new Move(new Square(x,y), targetSquare, this, board.getPiece(targetSquare)));
////            }
////            else if(board.getEnPassantTargetSquare().equals(targetSquare)) {
////                moves.add(new Move(new Square(x,y), targetSquare, this, board.getPiece(board.getEnPassantPieceSquare()), true,
////                        new Move(board.getEnPassantPieceSquare(), null, board.getPiece(board.getEnPassantPieceSquare()), null)));
////            }
////        }
////
////        return moves;
////    }

    @Override
    public boolean isValidMove(Square fromSquare, Square targetSquare, Board board) {

        if(!super.isValidMove(fromSquare, targetSquare, board)) {
            return false;
        }

        int direction = this.color == Piece.WHITE ? 1 : -1;

        // one move forward
        // the target square must be empty
        if(fromSquare.getY() + direction == targetSquare.getY() && fromSquare.getX() == targetSquare.getX()
                && board.getPiece(targetSquare) == null) {
            // Possibly puts player in check.
            Board.Move move = getMove(fromSquare, targetSquare, board);
            if(board.wouldBeInCheck(move)) {
                return false;
            }
            return true;
        }

        // two moves forward
        // target square and intermediate square must be empty
        if(!hasMoved(fromSquare)
                && fromSquare.getY() + 2 * direction == targetSquare.getY() && fromSquare.getX() == targetSquare.getX()
                && board.getPiece(fromSquare.getX(), fromSquare.getY() + direction) == null
                && board.getPiece(targetSquare) == null) {
            // Possibly puts player in check.
            Board.Move move = getMove(fromSquare, targetSquare, board);
            if(board.wouldBeInCheck(move)) {
                return false;
            }
            return true;
        }

        Piece targetPiece = board.getPiece(targetSquare);

        return attacksSquare(fromSquare, targetSquare, board);
    }

    @Override
    public Board.Move getMove(Square fromSquare, Square targetSquare, Board board) {

        Board.Move changes = super.getMove(fromSquare, targetSquare, board);

        // if double move, change enpassant squares
        int direction = this.color == Piece.WHITE ? 1 : -1;
        if(fromSquare.getY() + 2 * direction == targetSquare.getY()) {
            changes.afterEnPassantTargetSquare = new Square(fromSquare.getX(), fromSquare.getY() + direction);
            changes.afterEnPassantPieceSquare = targetSquare;
        }
        // check if enpassant
        else if(Math.abs(fromSquare.getX() - targetSquare.getX()) == 1
                && board.getEnPassantTargetSquare() != null
                && board.getEnPassantTargetSquare().equals(targetSquare)) {
            Square enPassantPieceSquare = board.getEnPassantPieceSquare();
            changes.add(new Board.SquareChange(enPassantPieceSquare, board.getPiece(enPassantPieceSquare), null));
        }
        return changes;
    }

    /*
     *  returns true if the pawn is located on a possible initial square (second rank).
     */
    public boolean hasMoved(Square square) {
        return !((color == WHITE && square.getY() == 1) || (color == BLACK && square.getY() == 6));
    }

    @Override
    public boolean attacksSquare(Square fromSquare, Square targetSquare, Board board) {

        if(!super.attacksSquare(fromSquare, targetSquare, board)) {
            return false;
        }

        int direction = this.color == Piece.WHITE ? 1 : -1;

        // capture
        if(fromSquare.getY() + direction == targetSquare.getY()
                && Math.abs(fromSquare.getX() - targetSquare.getX()) == 1) {
            return true;
        }
        // enpassant capture
        if(fromSquare.getY() + direction == targetSquare.getY()
                && Math.abs(fromSquare.getX() - targetSquare.getX()) == 1
                && board.getEnPassantTargetSquare().equals(targetSquare)) {
            return true;
        }

        return false;
    }

    public ArrayList<Square> targetSquareList(Square fromSquare) {
        ArrayList<Square> list = new ArrayList<>();
        int direction = this.color == Piece.WHITE ? 1 : -1;
        list.add(new Square(fromSquare.getX() - 1, fromSquare.getY() + direction));
        list.add(new Square(fromSquare.getX(), fromSquare.getY() + direction));
        list.add(new Square(fromSquare.getX() + 1, fromSquare.getY() + direction));
        if(!this.hasMoved(fromSquare)) list.add(new Square(fromSquare.getX(), fromSquare.getY() + 2 * direction));

        Board.removeIllegalSquares(list);
        return list;
    }
}
