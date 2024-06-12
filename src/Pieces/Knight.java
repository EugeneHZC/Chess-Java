package Pieces;

import Game.Constants;
import Game.Player;
import Game.Type;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(int posX, int posY, Player player) {
        super(posX, posY, getImagePath(player), player, Type.KNIGHT);
    }

    private static String getImagePath(Player player) {
        if (player == Player.BLACK) {
            return "Pieces/Assets/bN.png";
        } else {
            return "Pieces/Assets/wN.png";
        }
    }

    @Override
    public ArrayList<Integer[]> getValidMoves(Piece[][] board) {
        ArrayList<Integer[]> validMoves = new ArrayList<>();
        Integer[] currentPos = this.getCurrentPos();

        Integer[][] directions = new Integer[][]{{1, 2}, {-1, 2}, {1, -2}, {-1, -2}, {2, -1}, {2, 1}, {-2, -1}, {-2, 1}};

        for (Integer[] d : directions) {
            int endRow = currentPos[0] + d[0];
            int endCol = currentPos[1] + d[1];

            if (0 <= endRow && endRow < Constants.ROW.getValue() && 0 <= endCol && endCol < Constants.COL.getValue()) {
                Integer[] endPos = new Integer[]{endRow, endCol};
                if (board[endRow][endCol] == null) {
                    validMoves.add(endPos);
                } else if (board[endRow][endCol].getPlayer() != this.getPlayer()) {
                    validMoves.add(endPos);
                }
            }
        }

        return validMoves;
    }
}
