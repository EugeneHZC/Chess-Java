package Pieces;

import Game.Player;

public class Knight extends Piece {
    public Knight(int posX, int posY, Player player) {
        super(posX, posY, getImagePath(player), player);
    }

    private static String getImagePath(Player player) {
        if (player == Player.BLACK) {
            return "Pieces/Assets/bN.png";
        } else {
            return "Pieces/Assets/wN.png";
        }
    }

    @Override
    public void validMoves() {

    }
}
