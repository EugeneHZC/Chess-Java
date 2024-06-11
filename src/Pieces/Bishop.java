package Pieces;

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
    public ArrayList<Integer[]> getValidMoves(Piece[][] board) {
        return new ArrayList<>();
    }
}
