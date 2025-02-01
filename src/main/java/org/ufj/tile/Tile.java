package org.ufj.tile;

import org.ufj.util.Point2D;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.abs;
import static org.ufj.game.GameConstants.TILE_OFFSET_Y_SIZE;
import static org.ufj.game.GameConstants.TILE_WIDTH_SIZE;

public class Tile extends TileImage {
    private Point2D tileStart;
    private Point2D tileEnd;
    private int tileWidth;
    private int tileHeight;
    private Polygon tilePolygon;
    private String type;
    private boolean isSlow;

    public Tile(Point2D start, Point2D end, String type, BufferedImage image) {
        this.tileStart = start;
        this.tileEnd = end;
        this.tileWidth = abs((int) (end.getX() - start.getX()));
        this.tileHeight = abs((int) (end.getY() - start.getY()));
        this.type = type;
        this.isSlow = !type.contains("Road");
        this.tilePolygon = new Polygon();
        this.image = image;
        tilePolygon.addPoint((int) start.getX() + TILE_WIDTH_SIZE / 2, (int) start.getY());
        tilePolygon.addPoint((int) end.getX() + TILE_WIDTH_SIZE / 2, (int) start.getY() + TILE_OFFSET_Y_SIZE);
        tilePolygon.addPoint((int) end.getX(), (int) end.getY() + TILE_OFFSET_Y_SIZE);
        tilePolygon.addPoint((int) start.getX(), (int) end.getY());
    }

    public Point2D getTileStart() {
        return tileStart;
    }

    public Point2D getTileEnd() {
        return tileEnd;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public Polygon getTilePolygon() {
        return tilePolygon;
    }

    public int getPolygonNPoints() {
        return tilePolygon.npoints;
    }

    public int[] getPolygonXPoints() {
        return tilePolygon.xpoints;
    }

    public int[] getPolygonYPoints() {
        return tilePolygon.ypoints;
    }

    public int getPolygonX(int index) {
        return tilePolygon.xpoints[index];
    }

    public int getPolygonY(int index) {
        return tilePolygon.ypoints[index];
    }

    public String getType() {
        return type;
    }

    public boolean isSlow() {
        return isSlow;
    }

    public void setTileStart(Point2D start) {
        this.tileStart = start;
        this.tileWidth = abs((int) (tileEnd.getX() - start.getX()));
        this.tileHeight = abs((int) (tileEnd.getY() - start.getY()));
    }

    public void setTileEnd(Point2D end) {
        this.tileEnd = end;
        this.tileWidth = abs((int) (end.getX() - tileStart.getX()));
        this.tileHeight = abs((int) (end.getY() - tileStart.getY()));
    }

    public void setType(String type) {
        this.type = type;
        this.isSlow = !type.contains("Road");
    }

    public void setSlow(boolean slow) {
        this.isSlow = slow;
    }

    public void setTilePolygon(Polygon polygon) {
        this.tilePolygon = polygon;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.drawPolygon(tilePolygon);
    }
}
