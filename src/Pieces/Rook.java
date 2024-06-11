package Pieces;

import Game.Constants;
import Game.Player;
import Game.Type;

import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(int posX, int posY, Player player) {
        super(posX, posY, getImagePath(player), player, Type.ROOK);
    }

    private static String getImagePath(Player player) {
        if (player == Player.BLACK) {
            return "Pieces/Assets/bR.png";
        } else {
            return "Pieces/Assets/wR.png";
        }
    }

    @Override
    public ArrayList<Integer[]> getValidMoves(Piece[][] board) {
        ArrayList<Integer[]> validMoves = new ArrayList<>();
        Integer[] currentPos = this.getCurrentPos();

        // move down
        for (int i = currentPos[1] + 1; i < Constants.ROW.getValue(); i++) {
            if (board[i][currentPos[0]] == null) {
                validMoves.add(new Integer[]{currentPos[0], i});
            } else if (board[i][currentPos[0]].getPlayer() != this.getPlayer()) {
                validMoves.add(new Integer[]{currentPos[0], i});
                break;
            } else {
                break;
            }
        }

        // move up
        for (int i = currentPos[1] - 1; i >= 0; i--) {
            if (board[i][currentPos[0]] == null) {
                validMoves.add(new Integer[]{currentPos[0], i});
            } else if (board[i][currentPos[0]].getPlayer() != this.getPlayer()) {
                validMoves.add(new Integer[]{currentPos[0], i});
                break;
            } else {
                break;
            }
        }

        // move left
        for (int i = currentPos[0] - 1; i >= 1; i--) {
            if (board[currentPos[1]][i] == null) {
                validMoves.add(new Integer[]{i, currentPos[1]});
            } else if (board[currentPos[1]][i].getPlayer() != this.getPlayer()) {
                validMoves.add(new Integer[]{i, currentPos[1]});
            } else {
                break;
            }
        }

        // move right
        for (int i = currentPos[0] + 1; i < Constants.COL.getValue(); i++) {
            if (board[currentPos[1]][i] == null) {
                validMoves.add(new Integer[]{i, currentPos[1]});
            } else if (board[currentPos[1]][i].getPlayer() != this.getPlayer()) {
                validMoves.add(new Integer[]{i, currentPos[1]});
                break;
            } else {
                break;
            }
        }

        return validMoves;
    }
}
