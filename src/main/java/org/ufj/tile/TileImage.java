package org.ufj.tile;

import java.awt.image.BufferedImage;

public class TileImage {
    protected BufferedImage image;

    public void setTileImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}