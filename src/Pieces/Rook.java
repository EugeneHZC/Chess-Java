package Pieces;

import Game.Player;
import Game.Type;

import java.util.ArrayList;

public class Rook extends Piece{
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
        return new ArrayList<>();
    }
}
