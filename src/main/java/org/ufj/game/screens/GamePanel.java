package org.ufj.game.screens;

import org.ufj.entity.CollisionDetector;
import org.ufj.entity.Entity;
import org.ufj.entity.Player;
import org.ufj.entity.obstacles.Obstacle;
import org.ufj.game.AudioPlayer;
import org.ufj.game.GameUI;
import org.ufj.game.KeyHandler;
import org.ufj.game.LapHandler;
import org.ufj.tile.MapLimits;
import org.ufj.tile.Tile;
import org.ufj.tile.TileManager;
import org.ufj.util.CharactersID;
import org.ufj.util.Point2D;

import java.awt.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.*;

import static org.ufj.game.GameConstants.*;

public class GamePanel extends JPanel implements Runnable {

    @SuppressWarnings("FieldCanBeLocal")
    private Thread thread;
    private boolean running = false;
    private final int FPS = 60;
    @SuppressWarnings("FieldCanBeLocal")
    private final double ns = 1000000000.0 / FPS;

    public final KeyHandler keyHandlerPlayer;
    public final Player player1;
    public final Player player2;

    private boolean player1CanLap = false;
    private boolean player2CanLap = false;
    private boolean player1OnLap = true;
    private boolean player2OnLap = true;
    private final GameUI ui;

    public final MapLimits mapLimits;

    public final TileManager tileM = new TileManager();
    private final CollisionDetector collisionDetector;
    private final LapHandler lapHandler = new LapHandler(tileM, false); // WIP

    private final AudioPlayer musicPlayer = new AudioPlayer(false);
    private final AudioPlayer audioPlayer = new AudioPlayer(false);

    public GamePanel(CharactersID[] characters) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);

        keyHandlerPlayer = new KeyHandler();
        addKeyListener(keyHandlerPlayer);
        Point2D[] startPositions = {new Point2D(tileM.trackStartPos), new Point2D(tileM.trackStartPos)};
        startPositions[0].move(-6, 6);
        startPositions[1].move(6, -6);
        boolean isPlayer1 = true;
        player1 = new Player(this, keyHandlerPlayer, startPositions[0], characters[0], isPlayer1);
        // System.out.println("Player Start X: " + tileM.trackStartPos.getX() + " Player Start Y: " + tileM.trackStartPos.getY());
        player2 = new Player(this, keyHandlerPlayer, startPositions[1], characters[1], !isPlayer1);

        mapLimits = new MapLimits(tileM.getLimits());
        Obstacle[] obstacles = tileM.getObstacles();
        collisionDetector = new CollisionDetector(
                Stream.concat(Stream.of(player1.getCharacter()), Arrays.stream(obstacles))
                        .toArray(Entity[]::new), mapLimits
        );

        ui = new GameUI(this);

        setDoubleBuffered(true);
        setBackground(new Color(173, 216, 230));
    }

    public void setUpGame() {
        playMusic();
    }

    public void init() {
        running = true;
        thread = new Thread(this);
        thread.start();
        System.out.println("Map Start X: " + MAP_START_X + " Map Start Y: " + MAP_START_Y);
        System.out.println("Map Width: " + MAP_WIDTH * TILE_WIDTH_SIZE + " Map Height: " + MAP_HEIGHT * TILE_HEIGHT_SIZE);
    }

    @Override
    public void run() {
        long start;
        long last = System.nanoTime();
        long elapsed;
        double delta = 0;

        while (running) {
            start = System.nanoTime();
            elapsed = start - last;
            delta += elapsed / ns;
            last = start;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player1.update(collisionDetector);
        player2.update(collisionDetector);
        gameLogic();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        player1.draw(g2);
        player2.draw(g2);
//        lapHandler.drawCheckpoints(g2);
        ui.draw(g2);
        g2.dispose();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public TileManager getTileManager() {
        return tileM;
    }

    public void playMusic() {
        musicPlayer.setFile(3);
        musicPlayer.play();
        musicPlayer.loop();
    }

    public void playSound(int i) {
        audioPlayer.setFile(i);
        audioPlayer.play();
    }

    public void stopMusic() {
        musicPlayer.stop();
    }

    public void gameLogic() {
//        if (lapHandler.isFinished()) {
//            stopMusic();
//            System.out.println("Game Over");
//            stop();
//        }
//        if (player1.getCharacter().getCollisionBox())
        int[] mapStartIndex = tileM.getMapStartPos();
        Tile[][] mapTiles = tileM.getMapTiles();

        // TODO: Make it count only if enters from half left and exits from half right
        boolean player1InsideStart = mapTiles[mapStartIndex[0]][mapStartIndex[1]].getTilePolygon()
                .intersects(player1.getCharacter().getCollisionBox()) ||
                mapTiles[mapStartIndex[0]][mapStartIndex[1]].getTilePolygon()
                        .contains(player1.getCharacter().getCollisionBox());
        boolean player2InsideStart = mapTiles[mapStartIndex[0]][mapStartIndex[1]].getTilePolygon()
                .intersects(player2.getCharacter().getCollisionBox()) ||
                mapTiles[mapStartIndex[0]][mapStartIndex[1]].getTilePolygon()
                        .contains(player2.getCharacter().getCollisionBox());

        // Check if it's in the half left of the start line
        // boolean player1HalfLeft = player1.getCharacter().getCollisionBox().getBounds().getCenterX() < mapTiles[mapStartIndex[0]][mapStartIndex[1]].getPolygonX(0);
        // boolean player2HalfLeft = player2.getCharacter().getCollisionBox().getBounds().getCenterX() < mapTiles[mapStartIndex[0]][mapStartIndex[1]].getPolygonX(0);


        if (player1InsideStart) {
            player1OnLap = true;
        }
        if (player2InsideStart) {
            player2OnLap = true;
        }

        if (player1OnLap && !player1InsideStart) {
            player1CanLap = true;
        }
        if (player2OnLap && !player2InsideStart) {
            player2CanLap = true;
        }

        if (player1CanLap && player1InsideStart) {
            player1CanLap = false;
            player1OnLap = false;
            player1.getCharacter().completeLap();
        }
        if (player2CanLap && player2InsideStart) {
            player2CanLap = false;
            player2OnLap = false;
            player2.getCharacter().completeLap();
        }

        if (player1.getCharacter().getLaps() == 3) {
            stopMusic();
            JOptionPane.showMessageDialog(this, "Player 1 wins!");
            stop();
        }

        if (player2.getCharacter().getLaps() == 3) {
            stopMusic();
            JOptionPane.showMessageDialog(this, "Player 2 wins!");
            stop();
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}