package org.ufj.entity.obstacles;

import org.ufj.util.Point2D;
import org.ufj.util.RotatedRectangle;
import org.ufj.tile.Tile;

import java.io.IOException;

public class SmallConiferTree extends Obstacle {
    public SmallConiferTree(Point2D position) {
        this.name = "SmallConiferTree";
        this.collisionOffsetX = 5; // Each side
        this.collisionOffsetY = 2; // Only the bottom
        this.tileWidth = 14;
        this.tileHeight = 36;

        try {
            this.sprite = loadImage("/assets/tiles/18.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.position = position;
        this.position.move((double) -tileWidth / 2 - 1, (double) -tileHeight / 2 - 1);
        this.tilePositions = new Tile(position,
                new Point2D(position.getX() + tileWidth, position.getY() + tileHeight), "Tree", sprite);

        // Manually set the collision box distance from the tile origin
        int collisionStartFromTileOriginX = collisionOffsetX; // The empty space on the right
        int collisionStartFromTileOriginY = tileHeight - 9; // The top of the tree

        double collisionStartPointX = position.getX() + collisionStartFromTileOriginX;
        double collisionStartPointY = position.getY() + collisionStartFromTileOriginY;

        this.collisionWidth = tileWidth - collisionOffsetX * 2;
        this.collisionHeight = tileHeight - collisionStartFromTileOriginY - collisionOffsetY; // To remove the empty space at the bottom

        this.collisionPosition = new Point2D(collisionStartPointX, collisionStartPointY);
        this.collisionBox = new RotatedRectangle(collisionPosition, collisionWidth, collisionHeight, 0);
        this.isSolid = true;
    }
}
