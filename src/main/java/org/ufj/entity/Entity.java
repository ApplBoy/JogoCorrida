package org.ufj.entity;

import org.ufj.util.Point2D;
import org.ufj.util.RotatedRectangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Entity {
    protected String name;
    public Point2D position;
    protected RotatedRectangle collisionBox;
    protected BufferedImage sprite;
    protected boolean isSolid = false;

    // Default Constructor
    public Entity() {
        this.position = new Point2D(0, 0);
        this.collisionBox = new RotatedRectangle(position, 0, 0, 0);
        this.name = "Entity";
    }

    // Copy Constructor
    public Entity(Entity entity) {
        this.position = entity.position;
        this.collisionBox = entity.collisionBox;
        this.sprite = entity.sprite;
        this.isSolid = entity.isSolid;
        this.name = entity.name;
    }

    public void setPosition(Point2D position) {
        this.position = position;
        collisionBox.setLocation((int) position.getX(), (int) position.getY());
    }

    public void setCollisionBox(RotatedRectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public String getName() {
        return name;
    }

    public RotatedRectangle getCollisionBox() {
        return collisionBox;
    }

    public Rectangle getBounds() {
        return collisionBox.getBounds();
    }

    protected BufferedImage loadImage(String path) throws IOException {
        return read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSolidity(boolean solid) {
        isSolid = solid;
    }

    public boolean isSolid() {
        return isSolid;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "position=" + position +
                ", collisionBox=" + collisionBox +
                ", sprite=" + sprite +
                ", isSolid=" + isSolid +
                '}';
    }
}