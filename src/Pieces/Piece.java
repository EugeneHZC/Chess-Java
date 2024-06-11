package Pieces;

import Game.Constants;
import Game.Player;
import Game.Type;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public abstract class Piece {
    private int posX;
    private int posY;
    private final Image image;
    private final Player player;
    private final ArrayList<Integer[]> validMoves = new ArrayList<>();
    private Integer[] currentPos;
    private final Type type;
    private boolean isFirstMove = true;

    public Piece(int posX, int posY, String imagePath, Player player, Type type) {
        this.posX = posX;
        this.posY = posY;
        this.image = loadImage(imagePath);
        this.player = player;
        this.currentPos = new Integer[]{this.posX / Constants.CELL_DIMENSION.getValue(), this.posY / Constants.CELL_DIMENSION.getValue()};
        this.type = type;
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

    public boolean getIsFirstMove() {
        return isFirstMove;
    }

    public void setIsFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    public Type getType() {
        return type;
    }

    public Integer[] getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos() {
        this.currentPos = new Integer[]{this.posX / Constants.CELL_DIMENSION.getValue(), this.posY / Constants.CELL_DIMENSION.getValue()};
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

    public abstract ArrayList<Integer[]> getValidMoves(Piece[][] board);
}
