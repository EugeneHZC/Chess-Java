package Pieces;

import Game.Constants;
import Game.Player;
import Game.Type;

import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(int posX, int posY, Player player) {
        super(posX, posY, getImagePath(player), player, Type.BISHOP);
    }

    private static String getImagePath(Player player) {
        if (player == Player.BLACK) {
            return "Pieces/Assets/bB.png";
        } else {
            return "Pieces/Assets/wB.png";
        }
    }

    @Override
    public ArrayList<Integer[]> getMoves(Piece[][] board) {
        ArrayList<Integer[]> validMoves = new ArrayList<>();
        Integer[] currentPos = this.getCurrentPos();

        Integer[][] directions = new Integer[][]{{1, 1}, {-1, 1}, {1, -1}, {-1, -1}};

        for (Integer[] d : directions) {
            for (int i = 1; i < Constants.ROW.getValue(); i++) {
                int endRow = currentPos[0] + d[0] * i;
                int endCol = currentPos[1] + d[1] * i;

                if (0 <= endRow && endRow < Constants.ROW.getValue() && 0 <= endCol && endCol < Constants.COL.getValue()) {
                    Integer[] endPos = new Integer[]{endRow, endCol};
                    if (board[endRow][endCol] == null) {
                        validMoves.add(endPos);
                    } else if (board[endRow][endCol].getPlayer() != this.getPlayer()) {
                        validMoves.add(endPos);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }

        return validMoves;
    }
}
