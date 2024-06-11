package Game;

import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Game extends JPanel implements MouseListener, ActionListener {
    private final int rows = Constants.ROW.getValue();
    private final int cols = Constants.COL.getValue();
    private final int pieceOffSetInCell = 5;
    private final int cellDimension = Constants.CELL_DIMENSION.getValue();

    private final Piece[][] board = new Piece[rows][cols];

    private final int windowsWidth = (int) (this.cellDimension * (cols + 1.0 / 4.0));
    private final int windowsHeight = (int) (this.cellDimension * (cols + 1.0 / 2.0) + this.cellDimension);

    private Piece selectedPiece;
    private boolean isWhiteFirst = true;
    private boolean isWhiteChecked = false;
    private boolean isBlackChecked = false;

    private Timer timer;
    private final int delay = 5;

    ArrayList<Integer[]> validMoves = new ArrayList<>();

    public Game() {
        addMouseListener(this);

        // Black side
        board[0][0] = new Rook(pieceOffSetInCell, pieceOffSetInCell, Player.BLACK);
        board[0][1] = new Knight(cellDimension + pieceOffSetInCell, pieceOffSetInCell, Player.BLACK);
        board[0][2] = new Bishop(2 * cellDimension + pieceOffSetInCell, pieceOffSetInCell, Player.BLACK);
        board[0][3] = new Queen(3 * cellDimension + pieceOffSetInCell, pieceOffSetInCell, Player.BLACK);
        board[0][4] = new King(4 * cellDimension + pieceOffSetInCell, pieceOffSetInCell, Player.BLACK);
        board[0][5] = new Bishop(5 * cellDimension + pieceOffSetInCell, pieceOffSetInCell, Player.BLACK);
        board[0][6] = new Knight(6 * cellDimension + pieceOffSetInCell, pieceOffSetInCell, Player.BLACK);
        board[0][7] = new Rook(7 * cellDimension + pieceOffSetInCell, pieceOffSetInCell, Player.BLACK);

        // White side
        board[7][0] = new Rook(pieceOffSetInCell, 7 * cellDimension + pieceOffSetInCell, Player.WHITE);
        board[7][1] = new Knight(cellDimension + pieceOffSetInCell, 7 * cellDimension + pieceOffSetInCell, Player.WHITE);
        board[7][2] = new Bishop(2 * cellDimension + pieceOffSetInCell, 7 * cellDimension + pieceOffSetInCell, Player.WHITE);
        board[7][3] = new Queen(3 * cellDimension + pieceOffSetInCell, 7 * cellDimension + pieceOffSetInCell, Player.WHITE);
        board[7][4] = new King(4 * cellDimension + pieceOffSetInCell, 7 * cellDimension + pieceOffSetInCell, Player.WHITE);
        board[7][5] = new Bishop(5 * cellDimension + pieceOffSetInCell, 7 * cellDimension + pieceOffSetInCell, Player.WHITE);
        board[7][6] = new Knight(6 * cellDimension + pieceOffSetInCell, 7 * cellDimension + pieceOffSetInCell, Player.WHITE);
        board[7][7] = new Rook(7 * cellDimension + pieceOffSetInCell, 7 * cellDimension + pieceOffSetInCell, Player.WHITE);

        // Black pawns
        for (int col = 0; col < cols; col++) {
            board[1][col] = new Pawn(col * cellDimension + pieceOffSetInCell, cellDimension + pieceOffSetInCell, Player.BLACK);
        }

        // White pawns
        for (int col = 0; col < cols; col++) {
            board[6][col] = new Pawn(col * cellDimension + pieceOffSetInCell, cellDimension * 6 + pieceOffSetInCell, Player.WHITE);
        }

        timer = new Timer(this.delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {
        // background
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, this.windowsWidth, this.windowsHeight);

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

        for (int row = 0; row < rows; row++) {
            for (Piece piece : board[row]) {
                if (piece != null) {
                    graphics.drawImage(piece.getImage(), piece.getPosX(), piece.getPosY(), this);
                }
            }
        }

        graphics.setColor(Color.black);
        graphics.setFont(new Font("arial", Font.BOLD, 30));
        if (isWhiteFirst) {
            graphics.drawString("White's turn", 10, this.windowsHeight - this.cellDimension);
        } else {
            graphics.drawString("Black's turn", 10, this.windowsHeight - this.cellDimension);
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
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        int rowClicked = y / cellDimension;
        int colClicked = x / cellDimension;

        // Check if a piece is selected
        if (board[rowClicked][colClicked] != null && selectedPiece == null) {
            // If clicked on a piece, the piece will be selected for next move
            selectedPiece = board[rowClicked][colClicked];
        } else if (selectedPiece == board[rowClicked][colClicked]) {
            // If clicked on a piece and click on the same piece, the piece will not be selected for next move
            selectedPiece = null;
        } else if (selectedPiece != board[rowClicked][colClicked] &&
                selectedPiece != null &&
                board[rowClicked][colClicked] != null &&
                selectedPiece.getPlayer() == board[rowClicked][colClicked].getPlayer()) {
            // If clicked on a piece and click on another piece with same color, the other piece will be selected for next move
            selectedPiece = board[rowClicked][colClicked];
        }

        // Move piece
        if (selectedPiece != null) {
            // Check whether the selected piece is a black or white piece (different color pieces may have different valid moves)
            validMoves = selectedPiece.getValidMoves(board);

            // If a black piece is clicked while it is white's turn, the piece will not be selected and vice versa
            if ((selectedPiece.getPlayer() == Player.BLACK && isWhiteFirst) ||
                    (selectedPiece.getPlayer() == Player.WHITE && !isWhiteFirst)) {
                selectedPiece = null;
            }

            // for loop will run if clicked on the board the second time after selecting a piece
            if (board[rowClicked][colClicked] != selectedPiece) {
                for (Integer[] integers : validMoves) {
                    // If the move is in the piece's valid moves, then it makes the move (including capturing a piece)
                    if (Arrays.equals(integers, new Integer[]{colClicked, rowClicked})) {
                        assert selectedPiece != null;

                        selectedPiece.setIsFirstMove(false);

                        // Setting new piece position as the selected piece, setting old position as null (represents no piece)
                        board[rowClicked][colClicked] = selectedPiece;
                        board[selectedPiece.getCurrentPos()[1]][selectedPiece.getCurrentPos()[0]] = null;

                        // Setting the piece's old position to the new position
                        selectedPiece.setPosX(colClicked * this.cellDimension + pieceOffSetInCell);
                        selectedPiece.setPosY(rowClicked * this.cellDimension + pieceOffSetInCell);
                        selectedPiece.setCurrentPos();

                        isWhiteFirst = !isWhiteFirst;
                        break;
                    }
                }

                // deselect the piece after moving it, trying to make an invalid move or after capturing
                // only activates if there is a second click on the board
                selectedPiece = null;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
