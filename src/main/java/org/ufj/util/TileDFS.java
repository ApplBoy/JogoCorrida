package org.ufj.util;

import org.ufj.tile.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import org.locationtech.jts.geom.Geometry;
//import org.locationtech.jts.geom.GeometryFactory;
//import org.locationtech.jts.geom.Polygon;

public class TileDFS {
    public Tile[][] tiles;
    public boolean[][] visited;
    public int rows;
    public int cols;
    public int startX;
    public int startY;
    public Set<Tile>[] checkpoints;
    public List<Polygon> checkpointPolygons;
    private int checkpointGroup = 0;
    public int minTilesForEachCheckpoint = 3;
    public boolean trackIsClockwise;


    public TileDFS(Tile[][] tiles, int[] start, boolean trackIsClockwise) {
        this.tiles = tiles;
        this.rows = tiles.length;
        this.cols = tiles[0].length;
        this.visited = new boolean[rows][cols];
        this.startX = start[0];
        this.startY = start[1];

        // Count how many road tiles are in the map
        int roadTiles = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (tiles[i][j].getType().contains("Road")) {
                    roadTiles++;
                }
            }
        }
        this.checkpoints = new HashSet[roadTiles];
        for (int i = 0; i < roadTiles; i++) {
            checkpoints[i] = new HashSet<>();
        }

        this.checkpointPolygons = new ArrayList<>();
        this.trackIsClockwise = trackIsClockwise;
    }

    public void runDFS() {
        int i = startX;
        int j = startY;
        dfs(i, j);
    }

    // BUG: Hunt for bugs in here

    private void dfs(int i, int j) {
        if (i < 0 || i >= rows || j < 0 || j >= cols || visited[i][j]) {
            return;
        }
        visited[i][j] = true;
        // A messy solution, but we can go only one way when starting
        if (tiles[i][j].getType().contains("Start")) {
            checkpoints[checkpointGroup].add(tiles[i][j]);
            checkpointPolygons.add(tiles[i][j].getTilePolygon());
            if (trackIsClockwise) {
                dfs(i, j + 1); // Go up
                dfs(i - 1, j); // Go left
            } else {
                dfs(i, j - 1); // Go down
                dfs(i + 1, j); // Go right
            }
        } else if (tiles[i][j].getType().contains("Curve") && checkpoints[checkpointGroup].size() > minTilesForEachCheckpoint) {
            checkpoints[checkpointGroup].add(tiles[i][j]);
            checkpointGroup++;
            dfs(i + 1, j);
            dfs(i - 1, j);
            dfs(i, j + 1);
            dfs(i, j - 1);
        } else if (tiles[i][j].getType().contains("Road")) {
            checkpoints[checkpointGroup].add(tiles[i][j]);
            // Now we set the polygon on the current group to a union operation with the current tile polygon
//            checkpointPolygons.set(checkpointGroup, (Polygon) checkpointPolygons.get(checkpointGroup).union(tiles[i][j].getTilePolygon()));
            dfs(i + 1, j);
            dfs(i - 1, j);
            dfs(i, j + 1);
            dfs(i, j - 1);
        }
    }
}
