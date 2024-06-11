package Pieces;

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
        return new ArrayList<>();
    }
}
