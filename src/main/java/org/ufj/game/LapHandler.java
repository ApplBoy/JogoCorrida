package org.ufj.game;

import org.ufj.tile.Tile;
import org.ufj.tile.TileManager;
import org.ufj.util.Point2D;
import org.ufj.util.TileDFS;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class LapHandler {
    // This class will handle the laps of the game
    // Not the best implementation, I think it should be for each player
    private int currentLap;
    // The map will have automatically 3 laps to complete
    private final int totalLaps = 3;
    // The map also will need an automatic checkpoint system
    // This will be sooooooo "fun" to implement

    // Not used outside the constructor
    // private final TileManager tm;
    private final Set<Polygon> checkpoints;
    // Maybe... DFS? Yes, DFS.
    private final TileDFS tileDFS;
    private final String filePath;


    public LapHandler(TileManager tm, boolean trackIsClockwise) {
        this.currentLap = 0;
        // this.tm = tm;
        this.checkpoints = new HashSet<>();
        this.tileDFS = new TileDFS(tm.getMapTiles(), tm.getMapStartPos(), trackIsClockwise);
        initCheckpoints();
        this.filePath = "/maps/map01CHK.txt";
    }

    // First off, we need to separate the map for each curve with at least 3 straight roads.
    // Then we need to create a checkpoint system for each curve group.
    public void createCheckpoints(Set<Integer> pointX, Set<Integer> pointY) {
        Polygon checkpoint = new Polygon();
        for (int x : pointX) {
            for (int y : pointY) {
                checkpoint.addPoint(x, y);
            }
        }
        checkpoints.add(checkpoint);
    }

    public void createCheckpoints(Set<Point2D> points) {
        Polygon checkpoint = new Polygon();
        for (Point2D point : points) {
            checkpoint.addPoint((int) point.getX(), (int) point.getY());
        }
        checkpoints.add(checkpoint);
    }

    public boolean checkPoint(int x, int y) {
        for (Polygon checkpoint : checkpoints) {
            if (checkpoint.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPoint(Tile tile) {
        return checkPoint(tile.getPolygonX(0), tile.getPolygonY(0));
    }

    // BUG: What the... The blue polygon is messed up alright.
    public void initCheckpoints() {
        tileDFS.runDFS();
        Set<Tile>[] tileCheckpoints = tileDFS.checkpoints;
        // Now we need to get the starting point of all points and then end the polygon with the last point
        // of the checkpointGroup.
        // Or not, just add in the end positions for each tile too.
        for (Set<Tile> checkpoint : tileCheckpoints) {
            Set<Point2D> points = new HashSet<>();
            for (Tile tile : checkpoint) {
                for (int i = 0; i < tile.getPolygonNPoints(); i++) {
                    points.add(new Point2D(tile.getPolygonX(i), tile.getPolygonY(i)));
                }
            }

            createCheckpoints(points);
        }
    }

    public void drawCheckpoints(Graphics2D g) {
        g.setColor(Color.BLUE);
        for (Polygon checkpoint : checkpoints) {
            g.draw(checkpoint);
        }
    }

    public void nextLap() {
        this.currentLap++;
    }

    public boolean isFinished() {
        return this.currentLap >= this.totalLaps;
    }

    public int getCurrentLap() {
        return this.currentLap;
    }

    public int getTotalLaps() {
        return this.totalLaps;
    }
}
