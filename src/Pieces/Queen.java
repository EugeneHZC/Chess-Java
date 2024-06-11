package Pieces;

import Game.Player;
import Game.Type;

import java.util.ArrayList;

public class Queen extends Piece{
    public Queen(int posX, int posY, Player player) {
        super(posX, posY, getImagePath(player), player, Type.QUEEN);
    }

    private static String getImagePath(Player player) {
        if (player == Player.BLACK) {
            return "Pieces/Assets/bQ.png";
        } else {
            return "Pieces/Assets/wQ.png";
        }
    }

    @Override
    public ArrayList<Integer[]> getValidMoves(Piece[][] board) {
        return new ArrayList<>();
    }
}
