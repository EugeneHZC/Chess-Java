package Pieces;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public abstract class Piece {
    private int pos_x;
    private int pos_y;

    public Piece(int pos_x, int pos_y, String imagePath) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        loadImage(imagePath);
    }

    protected Image loadImage(String imagePath) {
        URL imageUrl = getClass().getClassLoader().getResource(imagePath);
        if (imageUrl != null) {
            return new ImageIcon(imageUrl).getImage();
        } else {
            System.err.println("Couldn't find file");
            return null;
        }
    }

    abstract void validMoves();
}
