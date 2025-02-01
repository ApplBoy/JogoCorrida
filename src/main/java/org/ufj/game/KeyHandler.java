package org.ufj.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import static java.awt.event.KeyEvent.*;

public class KeyHandler implements KeyListener {
    public boolean upPressedP1, downPressedP1, leftPressedP1, rightPressedP1;
    public boolean upPressedP2, downPressedP2, leftPressedP2, rightPressedP2;
    public enum Key {
        UP, DOWN, LEFT, RIGHT
    }
    public final Map<Key, Integer> keyMapPlayer1;
    public final Map<Key, Integer> keyMapPlayer2;

    public KeyHandler() {
        keyMapPlayer1 = new HashMap<>();
        keyMapPlayer1.put(Key.UP, VK_W);
        keyMapPlayer1.put(Key.DOWN, VK_S);
        keyMapPlayer1.put(Key.LEFT, VK_A);
        keyMapPlayer1.put(Key.RIGHT, VK_D);

        keyMapPlayer2 = new HashMap<>();
        keyMapPlayer2.put(Key.UP, VK_UP);
        keyMapPlayer2.put(Key.DOWN, VK_DOWN);
        keyMapPlayer2.put(Key.LEFT, VK_LEFT);
        keyMapPlayer2.put(Key.RIGHT, VK_RIGHT);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Player 1
        if (code == VK_ESCAPE) {
            System.exit(0);
        }
        if (code == keyMapPlayer1.get(Key.UP)) {
            upPressedP1 = true;
        }
        if (code == keyMapPlayer1.get(Key.DOWN)) {
            downPressedP1 = true;
        }
        if (code == keyMapPlayer1.get(Key.LEFT)) {
            leftPressedP1 = true;
        }
        if (code == keyMapPlayer1.get(Key.RIGHT)) {
            rightPressedP1 = true;
        }

        // Player 2
        if (code == keyMapPlayer2.get(Key.UP)) {
            upPressedP2 = true;
        }
        if (code == keyMapPlayer2.get(Key.DOWN)) {
            downPressedP2 = true;
        }
        if (code == keyMapPlayer2.get(Key.LEFT)) {
            leftPressedP2 = true;
        }
        if (code == keyMapPlayer2.get(Key.RIGHT)) {
            rightPressedP2 = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // Player 1
        if (code == keyMapPlayer1.get(Key.UP)) {
            upPressedP1 = false;
        }
        if (code == keyMapPlayer1.get(Key.DOWN)) {
            downPressedP1 = false;
        }
        if (code == keyMapPlayer1.get(Key.LEFT)) {
            leftPressedP1 = false;
        }
        if (code == keyMapPlayer1.get(Key.RIGHT)) {
            rightPressedP1 = false;
        }

        // Player 2
        if (code == keyMapPlayer2.get(Key.UP)) {
            upPressedP2 = false;
        }
        if (code == keyMapPlayer2.get(Key.DOWN)) {
            downPressedP2 = false;
        }
        if (code == keyMapPlayer2.get(Key.LEFT)) {
            leftPressedP2 = false;
        }
        if (code == keyMapPlayer2.get(Key.RIGHT)) {
            rightPressedP2 = false;
        }
    }
}
