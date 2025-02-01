package org.ufj.game.screens;

import org.ufj.util.CharactersID;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    public GameScreen(JFrame frame, CharactersID[] characters) {
        setLayout(new BorderLayout());

        GamePanel gamePanel = new GamePanel(characters);
        add(gamePanel, BorderLayout.CENTER);

        gamePanel.setUpGame();
        gamePanel.init();
        gamePanel.requestFocusInWindow();
    }
}