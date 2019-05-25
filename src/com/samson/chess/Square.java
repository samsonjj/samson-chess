package com.samson.chess;

// @Immutable
public final class Square {
    private final int x, y;
    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public char getFile() {
        return intToFile(x);
    }
    public char getRank() {
        return intToRank(y);
    }
    public static char intToFile(int x) {
        return "abcdefgh".charAt(x);
    }
    public static char intToRank(int y) {
        return "12345678".charAt(y);
    }
    public static int fileToInt(char file) {
        int result = "abcdefgh".indexOf(file);
        if(result < 0) {
            throw new IllegalArgumentException("Illegal (chess) file character.");
        }
        return result;
    }
    public static int rankToInt(char rank) {
        int result = "12345678".indexOf(rank);
        if(result < 0) {
            throw new IllegalArgumentException("Illegal rank character.");
        }
        return result;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Square)) return false;
        Square square = (Square) obj;
        return square.getX() == this.x && square.getY() == this.y;
    }
}