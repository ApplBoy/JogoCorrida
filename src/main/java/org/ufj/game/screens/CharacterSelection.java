package org.ufj.game.screens;

import org.ufj.game.Janela;
import org.ufj.util.CharactersID;

import javax.swing.*;
import java.awt.*;

public class CharacterSelection extends JPanel {
    private CharactersID selectedPlayerCharacter = CharactersID.NONE;
    private CharactersID selectedOpponentCharacter = CharactersID.NONE;
    private final CharactersID[] characters = new CharactersID[2];

    public CharacterSelection(JFrame frame) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Character Selection", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel characterPanel = getjPanel();

        add(characterPanel, BorderLayout.CENTER);

        // Create a button to start the game as debug
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startGameButton.addActionListener(e -> {
            // It needs to start a new frame with GameScreen.
            // Not to switch the content pane.
            // And it needs to send in the selected character (or if it's debug mode)
            frame.dispose();
//                Janela janela = new Janela("GameScreen");
            characters[0] = selectedPlayerCharacter;
            characters[1] = selectedOpponentCharacter;
            Janela janela = new Janela("GameScreen", characters);
        });
        add(startGameButton, BorderLayout.SOUTH);
    }

    private JPanel getjPanel() {
        JPanel characterPanel = new JPanel(new GridLayout(0, 1, 10, 10));

        // Add character buttons to the panel
        // Which will send a value to the GameScreen constructor
        JButton characterButton1 = getRButton();
        characterPanel.add(characterButton1);

        JButton characterButton2 = getBButton();
        characterPanel.add(characterButton2);

        JButton characterButton3 = getGButton();
        characterPanel.add(characterButton3);
        return characterPanel;
    }

    private JButton getRButton() {
        JButton characterButton1 = new JButton("Red Car");
        characterButton1.setFont(new Font("Arial", Font.PLAIN, 18));
        characterButton1.addActionListener(e -> {
            if (selectedPlayerCharacter == CharactersID.NONE) {
                selectedPlayerCharacter = CharactersID.RED_CAR;
                characterButton1.setText("Red Car (Selected)");
            } else if (
                    selectedOpponentCharacter == CharactersID.NONE &&
                    selectedPlayerCharacter != CharactersID.RED_CAR
            ) {
                selectedOpponentCharacter = CharactersID.RED_CAR;
                characterButton1.setText("Red Car (Opponent)");
            }
        });
        return characterButton1;
    }

    private JButton getBButton() {
        JButton characterButton2 = new JButton("Blue Car");
        characterButton2.setFont(new Font("Arial", Font.PLAIN, 18));
        characterButton2.addActionListener(e -> {
            if (selectedPlayerCharacter == CharactersID.NONE) {
                selectedPlayerCharacter = CharactersID.BLUE_CAR;
                characterButton2.setText("Blue Car (Selected)");
            } else if (
                    selectedOpponentCharacter == CharactersID.NONE &&
                    selectedPlayerCharacter != CharactersID.BLUE_CAR
            ) {
                selectedOpponentCharacter = CharactersID.BLUE_CAR;
                characterButton2.setText("Blue Car (Opponent)");
            }
        });
        return characterButton2;
    }

    private JButton getGButton() {
        JButton characterButton3 = new JButton("Green Car");
        characterButton3.setFont(new Font("Arial", Font.PLAIN, 18));
        characterButton3.addActionListener(e -> {
            if (selectedPlayerCharacter == CharactersID.NONE) {
                selectedPlayerCharacter = CharactersID.GREEN_CAR;
                characterButton3.setText("Green Car (Selected)");
            } else if (
                    selectedOpponentCharacter == CharactersID.NONE &&
                    selectedPlayerCharacter != CharactersID.GREEN_CAR
            ) {
                selectedOpponentCharacter = CharactersID.GREEN_CAR;
                characterButton3.setText("Green Car (Opponent)");
            }
        });
        return characterButton3;
    }
}