package Pieces;

import Game.Player;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public abstract class Piece {
    private int posX;
    private int posY;
    private final Image image;
    private final Player player;

    public Piece(int posX, int posY, String imagePath, Player player) {
        this.posX = posX;
        this.posY = posY;
        this.image = loadImage(imagePath);
        this.player = player;
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

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Image getImage() {
        return image;
    }

    public Player getPlayer() {
        return player;
    }

    abstract void validMoves();
}
