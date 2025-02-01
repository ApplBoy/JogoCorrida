package org.ufj.game;

import org.ufj.game.screens.CharacterSelection;
import org.ufj.game.screens.GameScreen;
import org.ufj.game.screens.MainMenu;
import org.ufj.util.CharactersID;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Janela extends JFrame {
    private final JFrame janela = new JFrame("Jogo de Corrida");

    private void initialize() {
        try {
            // UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Nimbus Look and Feel
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // System default Look and Feel
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel"); // Motif Look and Feel
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); // GTK Look and Feel
        } catch (Exception e) {
            Logger.getLogger(Janela.class.getName()).log(Level.SEVERE, null, e);
        }

        janela.setDefaultCloseOperation(EXIT_ON_CLOSE);
        janela.setResizable(false);
        janela.setSize(800, 600);
    }

    private void setAsVisible() {
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    public Janela() {
        initialize();
        janela.setContentPane(new MainMenu(janela));
        setAsVisible();
    }

    public Janela(String panelName, CharactersID[] characters) {
        initialize();
        switch (panelName) {
            case "CharacterSelection":
                janela.setContentPane(new CharacterSelection(janela));
                break;
            case "GameScreen":
                janela.setContentPane(new GameScreen(janela, characters));
                break;
            default:
                janela.setContentPane(new MainMenu(janela));
        }
        setAsVisible();
    }
}
