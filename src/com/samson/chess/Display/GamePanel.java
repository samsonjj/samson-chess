package com.samson.chess.Display;

import com.samson.chess.Board;
import com.samson.chess.pieces.Piece;
import com.samson.chess.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class GamePanel extends JPanel implements MouseListener {

    private Board board;

    public static final Color LIGHT = new Color(240, 220, 139);
    public static final Color DARK = new Color(159, 122, 67);

    private HashMap<String, Image> pieceImages = new HashMap<>();

    Square highlightedSquare;

    public GamePanel() {
        super(new FlowLayout(), true);
        board = new Board();
        try {
            BufferedImage spriteSheet = ImageIO.read(new File("C:\\Users\\jonat\\home\\code\\samson-chess\\src\\com\\samson\\chess\\1280px-Chess_Pieces_Sprite.svg.png"));
            loadPieceImage("Kw", spriteSheet);
            loadPieceImage("Qw", spriteSheet);
            loadPieceImage("Bw", spriteSheet);
            loadPieceImage("Nw", spriteSheet);
            loadPieceImage("Rw", spriteSheet);
            loadPieceImage("Pw", spriteSheet);
            loadPieceImage("Kb", spriteSheet);
            loadPieceImage("Qb", spriteSheet);
            loadPieceImage("Bb", spriteSheet);
            loadPieceImage("Nb", spriteSheet);
            loadPieceImage("Rb", spriteSheet);
            loadPieceImage("Pb", spriteSheet);
        } catch(Exception e) {
            e.printStackTrace();
            throw new Error();
        }

        this.addMouseListener(this);

        this.setBounds(0, 0, 500, 500);
        this.setBackground(Color.GRAY);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(48, 48, 404, 404);
        graphics2D.setColor(new Color(159, 122, 67));
        graphics2D.fillRect(50, 50, 400, 400);
        graphics2D.setColor(new Color(240, 220, 139));
        for(int i = 0; i < 72; i += 2) {
            if(i % 9 == 8) continue;
            graphics2D.fillRect(50 + (i % 9) * 50, 50 + (i / 9) * 50, 50, 50);
        }
        Piece[][] pieces = board.getBoard();
        for(int i = 0; i < pieces.length; i++) {
            for(int j = 0; j < pieces[i].length; j++) {
                if(pieces[i][j] == null) continue;
                graphics2D.drawImage(pieceImages.get(String.format("%c%c", pieces[i][j].letter, pieces[i][j].color == Piece.WHITE ? 'w' : 'b')),
                        50 + i * 50, 50 + (7-j) * 50, 50, 50, null, null);
            }
        }

        // paint highlight on highlighted square
        graphics2D.setColor(Color.BLUE);
        if(highlightedSquare != null) {
            graphics2D.drawRect(50 + highlightedSquare.getX() * 50, 50 + (7-highlightedSquare.getY()) * 50, 50, 50);
        }
    }

    private void loadPieceImage(String pieceAndColor, BufferedImage spriteSheet) {
        int yBase = pieceAndColor.charAt(1) == 'w' ? 0 : 213;
        int xBase = 0;
        switch(pieceAndColor.charAt(0)) {
            case('Q'):
                xBase = 213;
                break;
            case('B'):
                xBase = 213 * 2;
                break;
            case('N'):
                xBase = 213 * 3;
                break;
            case('R'):
                xBase = 213 * 4;
                break;
            case('P'):
                xBase = 213 * 5;
                break;
        }
        this.pieceImages.put(pieceAndColor, spriteSheet.getSubimage(xBase, yBase, 213, 213));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    public Board getBoard() {
        return board;
    }
    public void clickSquare(int x, int y) {
        Square clickedSquare = new Square(x, y);
        if(highlightedSquare == null) {
            if(board.getPiece(clickedSquare) != null && board.getPiece(clickedSquare).color == board.getTurn()) {
                highlightedSquare = clickedSquare;
            }
            else {

            }
        }
        else if (board.isLegalMove(highlightedSquare, clickedSquare)) {
            board.performMove(highlightedSquare, clickedSquare);
            highlightedSquare = null;
        }
        else if(board.getPiece(clickedSquare) != null && board.getPiece(clickedSquare).color == board.getTurn()) {
            highlightedSquare = clickedSquare;
        }
        else {
            highlightedSquare = null;
        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        clickSquare((e.getX()-50) / 50, 7 - (e.getY()-50) / 50);
        this.repaint();
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

}
