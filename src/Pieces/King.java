package Pieces;

import Game.Constants;
import Game.Player;
import Game.Type;

import java.lang.constant.Constable;
import java.util.ArrayList;

public class King extends Piece {
    public King(int posX, int posY, Player player) {
        super(posX, posY, getImagePath(player), player, Type.KING);
    }

    private static String getImagePath(Player player) {
        if (player == Player.BLACK) {
            return "Pieces/Assets/bK.png";
        } else {
            return "Pieces/Assets/wK.png";
        }
    }

    @Override
    public ArrayList<Integer[]> getMoves(Piece[][] board) {
        Integer[] currentPos = this.getCurrentPos();
        ArrayList<Integer[]> validMoves = new ArrayList<>();

        Integer[][] directions = new Integer[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};

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
