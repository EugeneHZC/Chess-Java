import Game.Game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        Game game = new Game();

        jFrame.setTitle("Chess");
        jFrame.setBounds(20, 20, game.getWindowsWidth(), game.getWindowsHeight());
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setFocusTraversalKeysEnabled(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(game);
    }
}