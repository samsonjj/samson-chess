package com.samson.chess.pieces;

import com.samson.chess.*;

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
            return true;
        }

        // two moves forward
        // target square and intermediate square must be empty
        if(!hasMoved(this.color, fromSquare.getX(), fromSquare.getY())
                && fromSquare.getY() + 2 * direction == targetSquare.getY() && fromSquare.getX() == targetSquare.getX()
                && board.getPiece(fromSquare.getX(), fromSquare.getY() + direction) == null
                && board.getPiece(targetSquare) == null) {
            return true;
        }

        Piece targetPiece = board.getPiece(targetSquare);

        return attacksSquare(fromSquare, targetSquare, board);
    }

    @Override
    public Board.BoardChange performMove(Square fromSquare, Square targetSquare, Board board) {
        if(!this.isValidMove(fromSquare, targetSquare, board)) {
            throw new IllegalChessMoveException();
        }

        // if double move, change enpassant squares
        int direction = this.color == Piece.WHITE ? 1 : -1;
        if(fromSquare.getY() + 2 * direction == targetSquare.getY()) {
            board.setEnPassantTargetSquare(new Square(fromSquare.getX(), fromSquare.getY() + direction));
            board.setEnPassantPieceSquare(targetSquare);
        }
        // check if enpassant
        else if(Math.abs(fromSquare.getX() - targetSquare.getX()) == 1
                && board.getEnPassantTargetSquare().equals(targetSquare)) {
            Square enPassantPieceSquare = board.getEnPassantPieceSquare();
            Board.BoardChange changes = super.performMove(fromSquare, targetSquare, board);
            changes.add(new Board.SquareChange(enPassantPieceSquare, board.getPiece(enPassantPieceSquare), null));
            return changes;
        }

        return super.performMove(fromSquare, targetSquare, board);
    }

    /*
     *  returns true if the pawn is located on a possible initial square (second rank).
     */
    public static boolean hasMoved(boolean color, int x, int y) {
        return !((color == WHITE && y == 1) || (color == BLACK && y == 6));
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


}
