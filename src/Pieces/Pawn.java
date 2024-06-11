package Pieces;

import Game.Player;
import Game.Type;

import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(int posX, int posY, Player player) {
        super(posX, posY, getImagePath(player), player, Type.PAWN);
    }

    private static String getImagePath(Player player) {
        if (player == Player.BLACK) {
            return "Pieces/Assets/bp.png";
        } else {
            return "Pieces/Assets/wp.png";
        }
    }

    @Override
    public ArrayList<Integer[]> getValidMoves(Piece[][] board) {
        Integer[] currentPos = this.getCurrentPos();
        ArrayList<Integer[]> validMoves = new ArrayList<>();

        if (this.getPlayer() == Player.BLACK) {
            // Move forward
            if (board[currentPos[1] + 1][currentPos[0]] == null) {
                validMoves.add(new Integer[]{currentPos[0], currentPos[1] + 1});

                // If the pawn moves for the first time, the pawn can move two positions forward
                if (this.getIsFirstMove()) {
                    validMoves.add(new Integer[]{currentPos[0], currentPos[1] + 2});
                }
            }

            // If there are diagonals pieces, valid to captured by pawns
            if (board[currentPos[1] + 1][currentPos[0] - 1] != null) {
                validMoves.add(new Integer[]{currentPos[0] - 1, currentPos[1] + 1});
            }

            if (board[currentPos[1] + 1][currentPos[0] + 1] != null) {
                validMoves.add(new Integer[]{currentPos[0] + 1, currentPos[1] + 1});
            }
        } else {
            // Move forward
            if (board[currentPos[1] - 1][currentPos[0]] == null) {
                validMoves.add(new Integer[]{currentPos[0], currentPos[1] - 1});

                // If the pawn moves for the first time, the pawn can move two positions forward
                if (this.getIsFirstMove()) {
                    validMoves.add(new Integer[]{currentPos[0], currentPos[1] - 2});
                }
            }

            // If there are diagonals pieces, valid to captured by pawns
            if (board[currentPos[1] - 1][currentPos[0] - 1] != null) {
                validMoves.add(new Integer[]{currentPos[0] - 1, currentPos[1] - 1});
            }

            if (board[currentPos[1] - 1][currentPos[0] + 1] != null) {
                validMoves.add(new Integer[]{currentPos[0] + 1, currentPos[1] - 1});
            }
        }

        return validMoves;
    }
}
