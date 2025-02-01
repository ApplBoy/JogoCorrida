package org.ufj.game;

import org.ufj.game.screens.GamePanel;

import java.awt.*;

public class GameUI {
    private final GamePanel gp;

    public GameUI(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        if (gp.getPlayer1().getCharacter().getLaps() > gp.getPlayer2().getCharacter().getLaps()) {
            g2.drawString(gp.getPlayer1().getCharacter().getLaps() + " / 3", 10, 20);
        } else {
            g2.drawString(gp.getPlayer2().getCharacter().getLaps() + " / 3", 10, 20);
        }
    }
}
