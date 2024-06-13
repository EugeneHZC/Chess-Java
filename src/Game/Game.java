package Game;

import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Game extends JPanel implements MouseListener, ActionListener {
    private final int rows = Constants.ROW.getValue();
    private final int cols = Constants.COL.getValue();
    private final int pieceOffSetInCell = 5;
    private final int cellDimension = Constants.CELL_DIMENSION.getValue();

    private final Piece[][] board = new Piece[rows][cols];

    private final int windowsWidth = (int) (this.cellDimension * (cols + 1.0 / 4.0));
    private final int windowsHeight = (int) (this.cellDimension * (cols + 1.0 / 2.0) + this.cellDimension);

    private Piece selectedPiece;
    private boolean isWhiteTurn = true;
    private boolean isWhiteChecked = false;
    private boolean isBlackChecked = false;

    private Integer[] whiteKingPos;
    private Integer[] blackKingPos;

    private Timer timer;
    private final int delay = 5;

    ArrayList<Integer[]> moves = new ArrayList<>();

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

        whiteKingPos = new Integer[]{7, 4};
        blackKingPos = new Integer[]{0, 4};

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
        if (isWhiteTurn) {
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
        timer.start();
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
            moves = generateValidMoves(selectedPiece);

            // If a black piece is clicked while it is white's turn, the piece will not be selected and vice versa
            if ((selectedPiece.getPlayer() == Player.BLACK && isWhiteTurn) ||
                    (selectedPiece.getPlayer() == Player.WHITE && !isWhiteTurn)) {
                selectedPiece = null;
            }

            // for loop will run if clicked on the board the second time after selecting a piece
            if (board[rowClicked][colClicked] != selectedPiece) {
                for (Integer[] integers : moves) {
                    // If the move is in the piece's valid moves, then it makes the move (including capturing a piece)
                    if (Arrays.equals(integers, new Integer[]{rowClicked, colClicked})) {
                        assert selectedPiece != null;

                        selectedPiece.setIsFirstMove(false);

                        // Setting new piece position as the selected piece, setting old position as null (represents no piece)
                        board[rowClicked][colClicked] = selectedPiece;
                        board[selectedPiece.getCurrentPos()[0]][selectedPiece.getCurrentPos()[1]] = null;

                        // Setting the piece's old position to the new position
                        selectedPiece.setPosX(colClicked * this.cellDimension + pieceOffSetInCell);
                        selectedPiece.setPosY(rowClicked * this.cellDimension + pieceOffSetInCell);
                        selectedPiece.setCurrentPos();

                        // Updating both kings' positions
                        if (selectedPiece.getType() == Type.KING && selectedPiece.getPlayer() == Player.WHITE) {
                            whiteKingPos = new Integer[]{selectedPiece.getCurrentPos()[0], selectedPiece.getCurrentPos()[1]};
                        } else if (selectedPiece.getType() == Type.KING && selectedPiece.getPlayer() == Player.BLACK) {
                            blackKingPos = new Integer[]{selectedPiece.getCurrentPos()[0], selectedPiece.getCurrentPos()[1]};
                        }

                        moves = selectedPiece.getMoves(board);

                        isWhiteTurn = !isWhiteTurn;
                        break;
                    }
                }

                // deselect the piece after moving it, trying to make an invalid move or after capturing
                // only activates if there is a second click on the board
                selectedPiece = null;
            }
        }
    }

    private ArrayList<Integer[]> generateValidMoves(Piece selectedPiece) {
        ArrayList<Integer[]> possibleMoves = selectedPiece.getMoves(board);
        ArrayList<Integer[]> validMoves = new ArrayList<>();
        HashMap<String, ArrayList<Integer[]>> hashMap = getChecksAndPins();
        ArrayList<Integer[]> pins = hashMap.get("pins");
        ArrayList<Integer[]> checks = hashMap.get("checks");

        int kingRow;
        int kingCol;

        if (isWhiteTurn) {
            kingRow = whiteKingPos[0];
            kingCol = whiteKingPos[1];
        } else {
            kingRow = blackKingPos[0];
            kingCol = blackKingPos[1];
        }

        if (checks.toArray().length == 0) {
            this.isWhiteChecked = false;
            this.isBlackChecked = false;
        }

        if (this.isWhiteChecked || this.isBlackChecked) {
            if (checks.toArray().length == 1) {
                ArrayList<Integer[]> validSquares = new ArrayList<>();
                Integer[] check = checks.getFirst();

                if (board[check[0]][check[1]].getType() == Type.KNIGHT) {
                    // If there is a knight check, the valid moves are capturing the knight or moving away from the checked square
                    ArrayList<Integer[]> knightMoves = board[check[0]][check[1]].getMoves(board);

                    // Capturing the knight
                    Integer[] validSquare = new Integer[]{check[0], check[1]};
                    validMoves.add(validSquare);

                    // King move away from checked square
                    if (selectedPiece.getType() == Type.KING) {
                        validMoves.addAll(possibleMoves);
                    }

                    // If there is a possible move same with knight moves, means that square is in check, invalid move
                    for (Integer[] knightMove : knightMoves) {
                        for (Integer[] possibleMove : possibleMoves) {
                            if (Arrays.equals(knightMove, possibleMove)) {
                                validMoves.remove(possibleMove);
                                break;
                            }
                        }
                    }

                    return validMoves;
                } else if (board[check[0]][check[1]].getType() == Type.PAWN) {
                    Integer[] validSquare = new Integer[]{check[0], check[1]};
                    validMoves.add(validSquare);

                    if (selectedPiece.getType() == Type.KING) {
                        validMoves.addAll(possibleMoves);
                    }

                    return validMoves;
                } else {
                    for (int i = 1; i < this.rows; i++) {
                        // Squares between the king the checking piece is valid as it can block
                        // including the checking piece's position (i.e. capturing the checking piece)
                        Integer[] validSquare = new Integer[]{kingRow + check[2] * i, kingCol + check[3] * i};
                        validSquares.add(validSquare);
                        // Stops until it reaches the checking piece's position
                        if (validSquare[0].equals(check[0]) && validSquare[1].equals(check[1])) {
                            break;
                        }
                    }

                    // Go through the loop and remove all the possible moves that are not in the valid squares
                    for (Integer[] square : validSquares) {
                        for (int i = possibleMoves.toArray().length - 1; i >= 0; i--) {
                            if (Arrays.equals(square, possibleMoves.get(i)) && selectedPiece.getType() != Type.KING) {
                                validMoves.add(possibleMoves.get(i));
                                break;
                            }
                        }
                    }

                    return validMoves;
                }
            } else if (checks.toArray().length > 1 && selectedPiece.getType() == Type.KING) {
                // If there is a double check, it is impossible to block all, so only king can move
                possibleMoves = selectedPiece.getMoves(board);
                hashMap = getChecksAndPins();
                checks = hashMap.get("checks");

                ArrayList<Integer[]> invalidSquares = new ArrayList<>();

                for (Integer[] check : checks) {
                    for (int i = 1; i < this.rows; i++) {
                        // Squares between the king the checking piece is not valid as the king still can be attacked
                        Integer[] invalidSquare = new Integer[]{kingRow + check[2] * i, kingCol + check[3] * i};
                        invalidSquares.add(invalidSquare);
                        // Stops until it reaches the checking piece's position
                        if (invalidSquare[0].equals(check[0]) && invalidSquare[1].equals(check[1])) {
                            break;
                        }
                    }
                }

                // Add all the possible moves into the valid moves and then remove the invalid from the valid afterwards
                validMoves.addAll(possibleMoves);

                // Go through the loop and remove all the possible moves that are not in the valid squares (excluding the king)
                for (Integer[] square : invalidSquares) {
                    for (int i = possibleMoves.toArray().length - 1; i >= 0; i--) {
                        if (Arrays.equals(square, possibleMoves.get(i))) {
                            validMoves.remove(possibleMoves.get(i));
                            break;
                        }
                    }
                }

                return validMoves;
            } else {
                return new ArrayList<>();
            }
        } else {
            // If there is no check, any piece can be moved except pins
            // King cannot be moved to a checked square
            if (selectedPiece.getType() != Type.KING) {
                return possibleMoves;
            }

            for (Integer[] possibleMove : possibleMoves) {
                if (!isSquareAttacked(possibleMove[0], possibleMove[1], selectedPiece)) {
                    validMoves.add(possibleMove);
                }
            }

            return validMoves;
        }
    }

    private boolean isSquareAttacked(int row, int col, Piece selectedPiece) {
        Player ally;
        Player enemy;

        if (isWhiteTurn) {
            ally = Player.WHITE;
            enemy = Player.BLACK;
        } else {
            ally = Player.BLACK;
            enemy = Player.WHITE;
        }

        Integer[][] knightDirections = new Integer[][]{{1, 2}, {-1, 2}, {1, -2}, {-1, -2}, {2, -1}, {2, 1}, {-2, -1}, {-2, 1}};
        Integer[][] directions = new Integer[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};

        // Check for pawns attacks
        if (selectedPiece.getPlayer() == Player.WHITE && row < this.rows && col < this.cols) {
            if (board[row - 1][col - 1] != null && board[row - 1][col - 1].getType() == Type.PAWN && board[row - 1][col - 1].getPlayer() == Player.BLACK) {
                return true;
            } else if (board[row - 1][col + 1] != null && board[row - 1][col + 1].getType() == Type.PAWN && board[row - 1][col + 1].getPlayer() == Player.BLACK) {
                return true;
            }
        } else if (selectedPiece.getPlayer() == Player.BLACK && row < this.rows && col < this.cols) {
            if (board[row + 1][col - 1] != null && board[row + 1][col - 1].getType() == Type.PAWN && board[row + 1][col - 1].getPlayer() == Player.WHITE) {
                return true;
            } else if (board[row + 1][col + 1] != null && board[row + 1][col + 1].getType() == Type.PAWN && board[row + 1][col + 1].getPlayer() == Player.WHITE) {
                return true;
            }
        }

        // Check for knights attacks
        for (Integer[] direction : knightDirections) {
            int endRow = row + direction[0];
            int endCol = col + direction[1];

            if (0 <= endRow && endRow < this.rows && 0 <= endCol && endCol < this.cols) {
                Piece piece = board[endRow][endCol];

                if (piece != null && piece.getPlayer() == enemy && piece.getType() == Type.KNIGHT) {
                    return true;
                }
            }
        }

        // Check for rooks, bishops, queens attacks
        for (Integer[] direction : directions) {
            for (int i = 0; i < this.rows; i++) {
                int endRow = row + direction[0] * i;
                int endCol = col + direction[1] * i;

                if (0 <= endRow && endRow < this.rows && 0 <= endCol && endCol < this.cols) {
                    Piece piece = board[endRow][endCol];

                    if (piece != null && piece.getPlayer() == enemy) {
                        if ((direction[0] == 0 || direction[1] == 0) && (piece.getType() == Type.ROOK || piece.getType() == Type.QUEEN)) {
                            // If there is an enemy rook or a queen on the horizontal or vertical directions of king, that position is checked
                            return true;
                        } else if ((direction[0] != 0 && direction[1] != 0) && (piece.getType() == Type.BISHOP || piece.getType() == Type.QUEEN)) {
                            // If there is an enemy bishop or a queen on the horizontal or vertical directions of king, that position is checked
                            return true;
                        } else {
                            // If there are other pieces other than queen, rook and bishops, then that square is not checked
                            return false;
                        }
                    } else if (piece != null && piece.getPlayer() == ally) {
                        break;
                    }
                }
            }
        }

        // Check for opponent's king checks
        for (Integer[] direction : directions) {
            int endRow = row + direction[0] * 2;
            int endCol = col + direction[1] * 2;

            if (0 <= endRow && endRow < this.rows && 0 <= endCol && endCol < this.cols) {
                Piece piece = board[endRow][endCol];

                if (piece != null && piece.getType() == Type.KING && piece.getPlayer() == enemy) {
                    return true;
                }

                return false;
            }
        }

        return false;
    }

    private HashMap<String, ArrayList<Integer[]>> getChecksAndPins() {
        HashMap<String, ArrayList<Integer[]>> hashMap = new HashMap<>();

        // Pins: position of a single piece between ally king and enemy piece that is checking the king
        ArrayList<Integer[]> pins = new ArrayList<>();
        // checks: position of piece that is checking the king [row, col, row direction, col direction]
        ArrayList<Integer[]> checks = new ArrayList<>();

        Integer[][] directions = new Integer[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        Integer[][] knightDirections = new Integer[][]{{1, 2}, {-1, 2}, {1, -2}, {-1, -2}, {2, -1}, {2, 1}, {-2, -1}, {-2, 1}};

        Player ally;
        Player enemy;
        Integer[] kingPos;

        if (isWhiteTurn) {
            ally = Player.WHITE;
            enemy = Player.BLACK;
            kingPos = new Integer[]{whiteKingPos[0], whiteKingPos[1]};
        } else {
            ally = Player.BLACK;
            enemy = Player.WHITE;
            kingPos = new Integer[]{blackKingPos[0], blackKingPos[1]};
        }

        // Check for opponent's piece in king's surroundings
        for (int i = 0; i < directions.length; i++) {
            ArrayList<Integer[]> possiblePins = new ArrayList<>();

            for (int j = 1; j < this.rows; j++) {
                int endRow = kingPos[0] + directions[i][0] * j;
                int endCol = kingPos[1] + directions[i][1] * j;

                if (0 <= endRow && endRow < Constants.ROW.getValue() && 0 <= endCol && endCol < Constants.COL.getValue()) {
                    Piece endPiece = board[endRow][endCol];

                    // If there's an ally piece, it could possibly be a pin
                    if (endPiece != null && endPiece.getPlayer() == ally) {
                        // If there is already an ally piece before this, then both these pieces cannot be pins
                        // (moving any of them doesn't affect)
                        if (possiblePins.toArray().length == 0) {
                            possiblePins.add(new Integer[]{endRow, endCol, directions[i][0], directions[i][1]});
                        } else {
                            break;
                        }
                    } else if (endPiece != null && endPiece.getPlayer() == enemy) {
                        Type type = endPiece.getType();

                        // If there is an enemy piece at the king's surroundings, despite the pins in place
                        if ((i < 4 && type == Type.ROOK) || (4 <= i && type == Type.BISHOP) || type == Type.QUEEN) {

                            if (possiblePins.toArray().length == 0) {
                                // If there are no pins, means the enemy piece is checking us
                                checks.add(new Integer[]{endRow, endCol, directions[i][0], directions[i][1]});

                                // King is in check
                                if (isWhiteTurn) {
                                    this.isWhiteChecked = true;
                                } else {
                                    this.isBlackChecked = true;
                                }
                            } else {
                                // If there is a pin, means the enemy doesn't check us as there is a piece blocking
                                pins.add(possiblePins.getFirst());
                            }
                        }

                        break;
                    }
                } else {
                    break;
                }
            }

            // If the piece detected around the king is a pawn
            if ((4 <= i && i <= 5 && enemy == Player.BLACK) || (6 <= i && enemy == Player.WHITE)) {
                int endRow = kingPos[0] + directions[i][0];
                int endCol = kingPos[1] + directions[i][1];

                if (0 <= endRow && endRow < Constants.ROW.getValue() && 0 <= endCol && endCol < Constants.COL.getValue() &&
                        board[endRow][endCol] != null && board[endRow][endCol].getType() == Type.PAWN) {
                    Piece endPiece = board[endRow][endCol];

                    if (endPiece.getPlayer() == enemy) {
                        // If there is an enemy pawn piece at the king's surroundings

                        checks.add(new Integer[]{endRow, endCol, directions[i][0], directions[i][1]});

                        // King is in check
                        if (isWhiteTurn) {
                            this.isWhiteChecked = true;
                        } else {
                            this.isBlackChecked = true;
                        }
                    }
                }
            }
        }

        for (Integer[] knightDirection : knightDirections) {
            int endRow = kingPos[0] + knightDirection[0];
            int endCol = kingPos[1] + knightDirection[1];

            if (0 <= endRow && endRow < this.rows && 0 <= endCol && endCol <= this.cols) {
                Piece endPiece = board[endRow][endCol];

                if (endPiece != null && endPiece.getPlayer() == enemy && endPiece.getType() == Type.KNIGHT) {
                    checks.add(new Integer[]{endRow, endCol, knightDirection[0], knightDirection[1]});

                    // King is in check
                    if (isWhiteTurn) {
                        this.isWhiteChecked = true;
                    } else {
                        this.isBlackChecked = true;
                    }
                }
            }
        }

        hashMap.put("pins", pins);
        hashMap.put("checks", checks);

        return hashMap;
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
