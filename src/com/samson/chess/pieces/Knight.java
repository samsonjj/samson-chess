package com.samson.chess.pieces;

import com.samson.chess.Board;
import com.samson.chess.Square;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(boolean color) {
        super(color, 'N');
    }

    @Override
    public boolean isValidMove(Square fromSquare, Square targetSquare, Board board) {
        if(!super.isValidMove(fromSquare, targetSquare, board)) {
            return false;
        }
        System.out.println(board.getTurn());

        Board.Move move = getMove(fromSquare, targetSquare, board);
        System.out.println(board.getTurn());

        if(board.wouldBeInCheck(move)) {
            return false;
        }
        System.out.println(board.getTurn());


        return attacksSquare(fromSquare, targetSquare, board);
    }

    @Override
    public Board.Move getMove(Square fromSquare, Square targetSquare, Board board) {
        return super.getMove(fromSquare, targetSquare, board);
    }

    @Override
    public boolean attacksSquare(Square fromSquare, Square targetSquare, Board board) {
        if(!super.attacksSquare(fromSquare, targetSquare, board)) {
            return false;
        }

        // Make sure it is a square reachable by a knight.
        int dx = targetSquare.getX() - fromSquare.getX();
        int dy = targetSquare.getY() - fromSquare.getY();
        if(Math.abs(dx) + Math.abs(dy) != 3
                || targetSquare.getX() == fromSquare.getX()
                || targetSquare.getY() == fromSquare.getY()) {
            return false;
        }

        return true;
    }

    public ArrayList<Square> targetSquareList(Square fromSquare) {
        ArrayList<Square> list = new ArrayList<>();
        Square square;
        list.add(new Square(fromSquare.getX() - 1, fromSquare.getY() + 2));
        list.add(new Square(fromSquare.getX() - 1, fromSquare.getY() - 2));
        list.add(new Square(fromSquare.getX() + 1, fromSquare.getY() + 2));
        list.add(new Square(fromSquare.getX() + 1, fromSquare.getY() - 2));
        list.add(new Square(fromSquare.getX() - 2, fromSquare.getY() + 1));
        list.add(new Square(fromSquare.getX() - 2, fromSquare.getY() - 1));
        list.add(new Square(fromSquare.getX() + 2, fromSquare.getY() + 1));
        list.add(new Square(fromSquare.getX() + 2, fromSquare.getY() - 1));

        Board.removeIllegalSquares(list);

        return list;
    }
}

