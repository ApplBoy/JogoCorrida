package org.ufj.tile;

import java.awt.*;

public class MapLimits {

    // Well, let's remake this using polygons
    private Polygon boundaries;

    public MapLimits() {
        boundaries = new Polygon();
    }

    public MapLimits(Polygon boundaries) {
        this.boundaries = boundaries;
    }

    public boolean checkCollision(Rectangle rect) {
        return this.boundaries.intersects(rect);
    }

    public boolean isWithinBounds(Rectangle rect) {
        return this.boundaries.contains(rect);
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.draw(boundaries);
    }

    public void fill(Graphics2D g2d) {
        g2d.fill(boundaries);
    }

    public Polygon getLimits() {
        return boundaries;
    }

    public void setBoundaries(Polygon boundaries) {
        this.boundaries = boundaries;
    }
}
