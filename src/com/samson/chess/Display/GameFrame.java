package com.samson.chess.Display;

import com.samson.chess.Board;
import com.samson.chess.Square;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameFrame extends JFrame {

    GamePanel panel;

    Square highlightedSquare = null;

    public GameFrame() {
        super();
        panel = new GamePanel();
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.pack();
        this.setVisible(true);
    }

    public Board getBoard() {
        return panel.getBoard();
    }

}
