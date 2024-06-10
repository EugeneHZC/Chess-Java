package Pieces;

import Game.Player;

import java.awt.*;

public class Pawn extends Piece {
    private int pos_x;
    private int pos_y;
    private Player player;
    private Image image;

    public Pawn(int pos_x, int pos_y, Player player) {
        super(pos_x, pos_y, getImagePath(player));
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.player = player;
        this.image = loadImage(getImagePath(player));
    }


    private static String getImagePath(Player player) {
        if (player == Player.BLACK) {
            return "Pieces/Assets/bp.png";
        } else {
            return "Pieces/Assets/wp.png";
        }
    }

    @Override
    void validMoves() {

    }
}
