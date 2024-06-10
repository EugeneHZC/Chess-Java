package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener, ActionListener {
    private final int rows = 8;
    private final int cols = 8;

    private final int cellDimension = 80;

    private final String[][] board = new String[rows][cols];

    private final int windowsWidth = (int) (this.cellDimension * (cols + 1.0 / 4.0));
    private final int windowsHeight = (int) (this.cellDimension * (cols + 1.0 / 2.0));

    private Timer timer;
    private final int delay = 5;

    public Game() {
        // Black side
        board[0][0] = "bR";
        board[0][1] = "bN";
        board[0][2] = "bB";
        board[0][3] = "bK";
        board[0][4] = "bQ";
        board[0][5] = "bB";
        board[0][6] = "bN";
        board[0][7] = "bR";

        // White side
        board[7][0] = "wR";
        board[7][1] = "wN";
        board[7][2] = "wB";
        board[7][3] = "wQ";
        board[7][4] = "wK";
        board[7][5] = "wB";
        board[7][6] = "wN";
        board[7][7] = "wR";

        // Black pawns
        for (int i = 0; i < cols; i++) {
            board[1][i] = "bp";
        }

        // White pawns
        for (int i = 0; i < cols; i++) {
            board[6][i] = "wp";
        }

        timer = new Timer(this.delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {
        // drawing the board
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if ((col % 2 == 0 && row % 2 == 0) || (col % 2 == 1 && row % 2 == 1)) {
                    graphics.setColor(Color.white);
                    graphics.fillRect(col * cellDimension, row * cellDimension, cellDimension, cellDimension);
                } else {
                    graphics.setColor(Color.gray);
                    graphics.fillRect(col * cellDimension, row * cellDimension, cellDimension, cellDimension);
                }
            }
        }

        graphics.dispose();
    }

    public int getWindowsWidth() {
        return windowsWidth;
    }

    public int getWindowsHeight() {
        return windowsHeight;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
