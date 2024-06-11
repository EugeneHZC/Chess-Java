package Pieces;

import Game.Player;
import Game.Type;

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
    public ArrayList<Integer[]> getValidMoves(Piece[][] board) {
        return new ArrayList<>();
    }
}
