package org.ufj.game;

import java.awt.*;

public class GameConstants {
    // Tile Attributes
    // Each tile is 100x65 pixels, SE offset is 50x24 pixels, SW offset is -50x24 pixels,
    // NE offset is 50x-24 pixels, NW offset is -50x-24 pixels
    public static final int TILE_WIDTH = 100;
    public static final int TILE_HEIGHT = 65;
    public static final int TILE_OFFSET_X = 50;
    public static final int TILE_OFFSET_Y = 24;
    // dunno if this is necessary, but it's here
    public static final float TILE_RATIO = (float) TILE_WIDTH / (float) TILE_HEIGHT;
    public static final float MAP_SCALE = 0.6f;
    public static final int TILE_WIDTH_SIZE = (int) (TILE_WIDTH * MAP_SCALE);
    public static final int TILE_HEIGHT_SIZE = (int) (TILE_HEIGHT * MAP_SCALE);
    public static final int TILE_OFFSET_X_SIZE = (int) (TILE_OFFSET_X * MAP_SCALE);
    public static final int TILE_OFFSET_Y_SIZE = (int) (TILE_OFFSET_Y * MAP_SCALE);
    public static final int TILE_ANGLE = 27;

    // Map Attributes
    public static final int MAP_WIDTH = 16;
    public static final int MAP_HEIGHT = 12;
    public static final int MAP_OFFSET_X = 10;
    public static final int MAP_OFFSET_Y = 10;

    // Check user max screen resolution
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final double MAX_WIDTH = SCREEN_SIZE.getWidth();
    public static final double MAX_HEIGHT = SCREEN_SIZE.getHeight();

    // Screen Attributes
    public static final int WIDTH = (int) Math.min(MAX_WIDTH, TILE_WIDTH * MAP_WIDTH * MAP_SCALE + MAP_OFFSET_X);
    public static final int HEIGHT = (int) Math.min(MAX_HEIGHT, TILE_HEIGHT * MAP_HEIGHT * MAP_SCALE + MAP_OFFSET_Y);

    // Map Centering Attributes
    public static final int MAP_START_X = (TILE_WIDTH_SIZE * MAP_WIDTH - TILE_OFFSET_X_SIZE) - (WIDTH / 2) - MAP_OFFSET_X * 13;
    public static final int MAP_START_Y = (TILE_HEIGHT_SIZE * MAP_HEIGHT - TILE_OFFSET_Y_SIZE) - (HEIGHT / 2) - MAP_OFFSET_Y * 12;

    // Legacy Code
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 12;
    public static final int SCREEN_WIDTH = TILE_WIDTH_SIZE * MAX_SCREEN_COL; //769 pixels
    public static final int SCREEN_HEIGHT = TILE_WIDTH_SIZE * MAX_SCREEN_ROW; //576 pixels
}
