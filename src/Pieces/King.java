package Pieces;

import Game.Player;

public class King extends Piece {
    public King(int posX, int posY, Player player) {
        super(posX, posY, getImagePath(player), player);
    }

    private static String getImagePath(Player player) {
        if (player == Player.BLACK) {
            return "Pieces/Assets/bK.png";
        } else {
            return "Pieces/Assets/wK.png";
        }
    }

    @Override
    public void validMoves() {

    }
}
