package Pieces;

import Game.Player;

public class Pawn extends Piece {
    public Pawn(int posX, int posY, Player player) {
        super(posX, posY, getImagePath(player), player);
    }

    private static String getImagePath(Player player) {
        if (player == Player.BLACK) {
            return "Pieces/Assets/bp.png";
        } else {
            return "Pieces/Assets/wp.png";
        }
    }

    @Override
    public void validMoves() {

    }
}
