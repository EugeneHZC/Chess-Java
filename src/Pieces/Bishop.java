package Pieces;

import Game.Player;

public class Bishop extends Piece{
    public Bishop(int posX, int posY, Player player) {
        super(posX, posY, getImagePath(player), player);
    }

    private static String getImagePath(Player player) {
        if (player == Player.BLACK) {
            return "Pieces/Assets/bR.png";
        } else {
            return "Pieces/Assets/wR.png";
        }
    }

    @Override
    public void validMoves() {

    }
}
