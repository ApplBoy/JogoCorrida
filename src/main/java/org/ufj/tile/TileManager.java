package org.ufj.tile;

import org.ufj.entity.obstacles.Obstacle;
import org.ufj.entity.obstacles.SmallConiferTree;
import org.ufj.entity.obstacles.SmallTree;
import org.ufj.util.Point2D;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.imageio.ImageIO.read;
import static org.ufj.game.GameConstants.*;

public class TileManager {
    private final TileImage[] tileImage;
    private final int[][] mapTileNum;
    private final int[][] obstaclesTileNum;
    private final Point2D[][] obstaclesTileOffset;
    private final Set<Integer> validNumbersForObstacles = new HashSet<>(Arrays.asList(40, 41, 42, 43, 46, 47, 48, 49, 50, 55));
    private int[] mapStartPos = {0, 0};
    private final Tile[][] mapTiles;
    private final Obstacle[][] obstacles;
    public Point2D trackStartPos;
    private MapLimits mapLimits;

    static private final int TILES_NUM = 71;

    private void initializeVariables() {
        for (int i = 0; i < org.ufj.game.GameConstants.MAX_SCREEN_COL; i++) {
            for (int j = 0; j < org.ufj.game.GameConstants.MAX_SCREEN_ROW; j++) {
                mapTileNum[i][j] = -1;
                obstaclesTileNum[i][j] = -1;
            }
        }
    }

    public TileManager() {
        tileImage = new TileImage[TILES_NUM];
        mapTiles = new Tile[MAX_SCREEN_COL][MAX_SCREEN_ROW];
        obstacles = new Obstacle[MAX_SCREEN_COL][MAX_SCREEN_ROW];
        mapTileNum = new int[MAX_SCREEN_COL][MAX_SCREEN_ROW];
        obstaclesTileNum = new int[MAX_SCREEN_COL][MAX_SCREEN_ROW];
        obstaclesTileOffset = new Point2D[MAX_SCREEN_COL][MAX_SCREEN_ROW];
        initializeVariables();
        getTileImage();
        loadMap("/maps/map01.txt");
        System.out.println("Map Tile 0,0: " + mapTiles[0][0].getTileStart() + " | " + mapTiles[0][0].getTileEnd());
    }

    @SuppressWarnings("unused")
    public TileManager(String mapPath) {
        tileImage = new TileImage[TILES_NUM];
        mapTiles = new Tile[MAX_SCREEN_COL][MAX_SCREEN_ROW];
        obstacles = new Obstacle[MAX_SCREEN_COL][MAX_SCREEN_ROW];
        mapTileNum = new int[MAX_SCREEN_COL][MAX_SCREEN_ROW];
        obstaclesTileNum = new int[MAX_SCREEN_COL][MAX_SCREEN_ROW];
        obstaclesTileOffset = new Point2D[MAX_SCREEN_COL][MAX_SCREEN_ROW];
        initializeVariables();
        getTileImage();
        loadMap(mapPath);
        System.out.println("Map Tile 0,0: " + mapTiles[0][0].getTileStart() + " | " + mapTiles[0][0].getTileEnd());
    }

    public void getTileImage() {
        String tileResourcePath = "/assets/tiles/";
        // read all tile images from the assets/tiles folder ending with .png
        // File folder = new File(Objects.requireNonNull(getClass().getResource(tileResourcePath)).getFile());
        // It needs to be sorted by the file name, so we can't use List.of, each tile has a number then its description
        // in the file name
        // List<String> tileNames = new java.util.ArrayList<>(List.of(Objects.requireNonNull(folder.list((dir, name) -> name.endsWith(".png")))));
        // tileNames.sort(String::compareTo);

        // Now all are renamed to numbers, like 00.png, 01.png, 02.png, 12.png, etc.
        List<String> tileNames = new ArrayList<>();

        for (int i = 0; i <= 70; i++) {
            String tileName = String.format("%02d.png", i);
            tileNames.add(tileName);
        }


        try {
            for (int i = 0; i < TILES_NUM; i++) {
                tileImage[i] = new TileImage();
                tileImage[i].setImage(read(Objects.requireNonNull(getClass().getResourceAsStream(tileResourcePath + tileNames.get(i)))));
            }
        } catch (IOException e) {
            Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void placeObstacle(int col, int row) {
        // Try another random to choose 2 possible obstacles
        int obstacleNum = new Random().nextInt(2);
        // And also it needs or a col-1 if it's not the first column and if col-1 isn't in the valid numbers
        // Or a row-1 if it's not the first row and if row-1 isn't in the valid numbers
        // Or a col+1 if it's not the last column and if col+1 isn't in the valid numbers
        // Or a row+1 if it's not the last row and if row+1 isn't in the valid numbers
        // First try is random, then it goes clockwise trying to find a valid position
        int obstacleCol = col, obstacleRow = row;
        Point2D offset = new Point2D(0, 0);
        boolean possible = true;

        switch (new Random().nextInt(4)) {
            case 0:
                if (col > 0 && !validNumbersForObstacles.contains(mapTileNum[col - 1][row])) {
                    obstacleCol = col - 1;
                    offset = new Point2D(5, -2);
                    break;
                }
            case 1:
                if (row > 0 && !validNumbersForObstacles.contains(mapTileNum[col][row - 1])) {
                    obstacleRow = row - 1;
                    offset = new Point2D(-2, 5);
                    break;
                }
            case 2:
                if (col < MAP_WIDTH - 1 && !validNumbersForObstacles.contains(mapTileNum[col + 1][row])) {
                    obstacleCol = col + 1;
                    offset = new Point2D(2, -5);
                    break;
                }
            case 3:
                if (row < MAP_HEIGHT - 1 && !validNumbersForObstacles.contains(mapTileNum[col][row + 1])) {
                    obstacleRow = row + 1;
                    offset = new Point2D(-5, 2);
                    break;
                }
            default:
                possible = false;
                break;
        }

        // Well randomly didn't work, so we need to check if it's possible
        // until case 3, because worst case scenario is that it's the case 4
        if (!possible) {
            if (col > 0 && !validNumbersForObstacles.contains(mapTileNum[col - 1][row])) {
                obstacleCol = col - 1;
                offset = new Point2D(-5, 0);
            } else if (row > 0 && !validNumbersForObstacles.contains(mapTileNum[col][row - 1])) {
                obstacleRow = row - 1;
                offset = new Point2D(0, -5);
            } else if (col < MAP_WIDTH - 1 && !validNumbersForObstacles.contains(mapTileNum[col + 1][row])) {
                obstacleCol = col + 1;
                offset = new Point2D(5, 0);
            }
        }

        if (possible) {
            switch (obstacleNum) {
                case 0:
                    obstaclesTileNum[obstacleCol][obstacleRow] = 61;
                    obstaclesTileOffset[obstacleCol][obstacleRow] = offset;
                    break;
                case 1:
                    obstaclesTileNum[obstacleCol][obstacleRow] = 18;
                    obstaclesTileOffset[obstacleCol][obstacleRow] = offset;
                    break;
            }
        }
    }

    public String tileType(int tileNum) {
        StringBuilder tileType = new StringBuilder();

        Set<Integer> roadTiles = new HashSet<>(validNumbersForObstacles);
        roadTiles.addAll(Arrays.asList(0, 1, 40, 41, 42, 43, 69, 70));

        if (roadTiles.contains(tileNum)) {
            tileType.append("Road");
        }

        Set<Integer> curveTiles = new HashSet<>(Arrays.asList(46, 47, 48, 49));
        if (curveTiles.contains(tileNum)) {
            switch (tileNum) {
                case 46:
                    tileType.append("CurveES");
                    break;
                case 47:
                    tileType.append("CurveNE");
                    break;
                case 48:
                    tileType.append("CurveNW");
                    break;
                case 49:
                    tileType.append("CurveWS");
                    break;
            }
        }
        // The nums in the set validNumbersForObstacles are also grass tiles
        // So it needs to be added along other tiles.
        Set<Integer> grassTiles = new HashSet<>(validNumbersForObstacles);
        grassTiles.addAll(Arrays.asList(2, 3, 35, 36, 37, 38, 39, 44, 45));
        if (grassTiles.contains(tileNum)) {
            tileType.append("Grass");
        }
        switch (tileNum) {
            // TODO: Add the other tile types
            // But I won't be using all of them right now...
            case 36:
                tileType.append("HillNE");
                break;
            case 37:
                tileType.append("HillSW");
                break;
            case 40:
            case 38:
                tileType.append("HillE");
                break;
            case 41:
            case 39:
                tileType.append("HillN");
                break;
            case 42:
            case 44:
                tileType.append("HillS");
                break;
            case 43:
            case 45:
                tileType.append("HillW");
                break;
        }

        return tileType.toString();
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(filePath));
            BufferedReader br = new BufferedReader(new InputStreamReader((is)));

            int col = 0, row = 0;

            while (col < MAP_WIDTH && row < MAP_HEIGHT) {
                String line = br.readLine();
                String[] numbers = line.split("\t");
                while (col < MAP_WIDTH) {
                    // Split the line by tabs
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    if (num == 69 || num == 70) {
                        mapStartPos = new int[]{col, row};
                    }

                    // Randomly, each 1/3 of chances, the sides of a road (in other words, num (40, 41, 42, 43, 46, 47, 48, 49, 50, 55, 69 and 70))
                    // will be an obstacle
                    if (validNumbersForObstacles.contains(num) && new Random().nextInt(3) == 0
                            && col < MAP_WIDTH - 2 && row < MAP_HEIGHT - 2) {
                        placeObstacle(col, row);
                    }

                    col++;
                }
                if (col == MAP_WIDTH) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            //e.printStackTrace();
            Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, e);
        }

        // Calculate each tile position
        int loops = 0, col = 0, row = 0, x = MAP_START_X, y = MAP_START_Y;
        Polygon mapDelimitations = new Polygon(); // 4 sides of the map
        Point2D[] corners = new Point2D[4]; // 4 corners of the map
        // 1st corner is the top left, 2nd is the top right, 3rd is the bottom left and 4th is the bottom right
        // Remember that.
        int cornerIndex = 0;
        boolean corner;

        while (col < MAP_WIDTH && row < MAP_HEIGHT) {
            // int tileNum = mapTileNum[col][row];
            // Draw the tile image at the correct position
            // each tile has an offset of gp.tileWidth and gp.tileHeight
            // on each side as shown in gp.tileOffsetX and gp.tileOffsetY

            // Well, it's isometric, so it's not that simple
            // so each column will need to go up -gp.tileOffsetX and -gp.tileOffsetY pixels
            mapTiles[col][row] = new Tile(new Point2D(x, y), new Point2D(x + TILE_WIDTH_SIZE - TILE_OFFSET_X_SIZE - 1, y + TILE_OFFSET_Y_SIZE), tileType(mapTileNum[col][row]), tileImage[mapTileNum[col][row]].image);

            // Now it needs to register the map limits, for each side of the map
            // To do it, we need to capture the points where a column or row starts/ends
            corner = col == 0 && row == 0 ||
                    col == MAP_WIDTH - 1 && row == MAP_HEIGHT - 1 ||
                    col == 0 && row == MAP_HEIGHT - 1 ||
                    col == MAP_WIDTH - 1 && row == 0;

            if (corner) {
                corners[cornerIndex] = new Point2D(x, y);
                cornerIndex++;
            }

            if (col == mapStartPos[0] && row == mapStartPos[1]) {
                trackStartPos = new Point2D(x, y);
            }

            if (obstaclesTileNum[col][row] != -1) {
                double obstacleX = x + (double) TILE_WIDTH_SIZE / 2;
                double obstacleY = y + (double) TILE_HEIGHT_SIZE / 2;
                Point2D obstaclePoint = new Point2D(obstacleX, obstacleY);
                obstaclePoint.move(obstaclesTileOffset[col][row]);
                if (obstaclesTileNum[col][row] == 18) {
                    obstacles[col][row] = new SmallConiferTree(obstaclePoint);
                } else if (obstaclesTileNum[col][row] == 61) {
                    obstacles[col][row] = new SmallTree(obstaclePoint);
                }
            }

            col++;
            x += TILE_WIDTH_SIZE - TILE_OFFSET_X_SIZE - 1;
            y += TILE_OFFSET_Y_SIZE;

            if (col == MAP_WIDTH) {
                loops++;
                col = 0;
                row++;
                // make X go back to the offset of the last column
                // To explain it: gp.mapStartX is the map start position
                // so we subtract the mapStartX by the width of the tile, leaning it to the right
                // of the last column, then we subtract the offset of the last column to lean it
                // a little bit to the right, then we multiply it by the number of loops to make it
                // linearly go to the left (the -1 is a minor adjustment)
                x = MAP_START_X - (TILE_WIDTH_SIZE - TILE_OFFSET_X_SIZE - 1) * loops;
                // and make Y go down to the offset of the last row
                // this is simpler, we just add the offset of the last row of the Y position,
                // and it'll go down correctly, and we multiply it by the number of loops
                // for linearity.
                y = MAP_START_Y + (TILE_OFFSET_Y_SIZE * loops);
            }
        }

        // Now we need to calculate the map limits
        // We have the corners, so we connect 1->2, 1->3, 2->4 and 3->4
        // 1->2 is the top side, 1->3 is the left side
        // 2->4 is the right side and 3->4 is the bottom side.

        // Top side
        mapDelimitations.addPoint((int) corners[0].getX() + TILE_WIDTH_SIZE / 2, (int) corners[0].getY());
        // Right side
        mapDelimitations.addPoint((int) corners[1].getX() + TILE_WIDTH_SIZE, (int) corners[1].getY() + TILE_HEIGHT_SIZE / 2);
        // Bottom side
        mapDelimitations.addPoint((int) corners[3].getX() + TILE_WIDTH_SIZE / 2, (int) corners[3].getY() + TILE_HEIGHT_SIZE - TILE_OFFSET_Y_SIZE);
        // Left side
        mapDelimitations.addPoint((int) corners[2].getX(), (int) corners[2].getY() + TILE_HEIGHT_SIZE / 2);

        // Wow, trial and error does wonders.
        this.mapLimits = new MapLimits(mapDelimitations);
    }

    private void drawTiles(Graphics2D g2) {
        // for each tile in the map
        for (int col = 0; col < MAX_SCREEN_COL; col++) {
            for (int row = 0; row < MAX_SCREEN_ROW; row++) {
                int tileNum = mapTileNum[col][row];
                int x = (int) mapTiles[col][row].getTileStart().getX();
                int y = (int) mapTiles[col][row].getTileStart().getY();
                // Draw the tile image at the correct position
                // each tile has an offset of gp.tileWidth and gp.tileHeight
                // on each side as shown in gp.tileOffsetX and gp.tileOffsetY
                g2.drawImage(mapTiles[col][row].getImage(), x, y, TILE_WIDTH_SIZE, TILE_HEIGHT_SIZE, null);

            }
        }
    }

    public void drawObstacles(Graphics2D g2) {
        for (int col = 0; col < MAX_SCREEN_COL; col++) {
            for (int row = 0; row < MAX_SCREEN_ROW; row++) {
                if (obstaclesTileNum[col][row] != -1) {
                    int obstacleStartX = obstacles[col][row].getTileStartX();
                    int obstacleStartY = obstacles[col][row].getTileStartY();
                    int obstacleWidth = obstacles[col][row].getTileWidth();
                    int obstacleHeight = obstacles[col][row].getTileHeight();
                    g2.drawImage(obstacles[col][row].getSprite(), obstacleStartX, obstacleStartY, obstacleWidth, obstacleHeight, null);
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        drawTiles(g2);
        drawObstacles(g2);
//        this.mapLimits.draw(g2);
    // Draw each tile in TilePosition
//        for (Tile[] tiles : mapTiles) {
//            for (Tile tile : tiles) {
//                tile.draw(g2);
//            }
//        }
//         mapTiles[mapStartPos[0]][mapStartPos[1]].draw(g2);
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }

    public int[][] getObstaclesTileNum() {
        return obstaclesTileNum;
    }

    public Tile[][] getMapTiles() {
        return mapTiles;
    }

    public Tile getTile(int col, int row) {
        return mapTiles[col][row];
    }

    public Tile getTile(Point2D position) {
        for (Tile[] tiles : mapTiles) {
            for (Tile tile : tiles) {
                if (tile.getTilePolygon().contains(position.getX(), position.getY())) {
                    return tile;
                }
            }
        }
        return null;
    }

    public Obstacle[][] getObstaclesMatrix() {
        return obstacles;
    }

    // This method is used to get all obstacles in a single array
    // But it filters the null values
    public Obstacle[] getObstacles() {
        return Arrays.stream(obstacles).flatMap(Arrays::stream).filter(Objects::nonNull).toArray(Obstacle[]::new);
    }

    public int[] getMapStartPos() {
        return mapStartPos;
    }

    public MapLimits getMapLimits() {
        return mapLimits;
    }

    public Polygon getLimits() {
        return mapLimits.getLimits();
    }
}
