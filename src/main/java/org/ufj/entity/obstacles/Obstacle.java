package org.ufj.entity.obstacles;

import org.ufj.entity.Entity;
import org.ufj.util.Point2D;
import org.ufj.tile.Tile;

public class Obstacle extends Entity {
    protected final boolean isDestructible = false;
    protected int health = 1;
    public final int maxHealth = 1;

    protected Point2D collisionPosition;
    protected Tile tilePositions;
    protected int tileWidth;
    protected int tileHeight;
    protected int collisionWidth;
    protected int collisionHeight;
    protected int collisionOffsetX;
    protected int collisionOffsetY;

    public boolean isDestructible() {
        return isDestructible;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getX() {
        return (int) position.getX();
    }

    public int getY() {
        return (int) position.getY();
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileStartX() {
        return (int) tilePositions.getTileStart().getX();
    }

    public int getTileStartY() {
        return (int) tilePositions.getTileStart().getY();
    }

    public int getTileEndX() {
        return (int) tilePositions.getTileEnd().getX();
    }

    public int getTileEndY() {
        return (int) tilePositions.getTileEnd().getY();
    }
}
