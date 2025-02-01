package org.ufj.game.screens;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    public MainMenu(JFrame frame) {
        JOptionPane.showMessageDialog(null,
                """
                        Este é o meu jogo de Corrida.
                        Controles:
                        - Player 1: Use W, A, S, D para movimentar
                        
                        - Player 2: Use as setas para movimentar
                        Pressione OK para começar!""",
                "Pise fundo!",
                JOptionPane.INFORMATION_MESSAGE);
        setLayout(new BorderLayout());

        // Create a label for the title
        JLabel titleLabel = new JLabel("Start Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Create a button to switch to the character selection screen
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startButton.addActionListener(e -> {
            frame.setContentPane(new CharacterSelection(frame));
            frame.revalidate();
        });
        add(startButton, BorderLayout.CENTER);
    }
}