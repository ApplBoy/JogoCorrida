package org.ufj.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public class RotatedRectangle extends Rectangle {
    private double rotationAngle;  // Rotation angle in radians

    public RotatedRectangle(int x, int y, int width, int height, double angle) {
        super(x, y, width, height);
        this.rotationAngle = Math.toRadians(angle);  // No rotation initially
    }

    public RotatedRectangle(Point2D position, int width, int height, double angle) {
        super((int) position.getX(), (int) position.getY(), width, height);
        this.rotationAngle = Math.toRadians(angle);  // No rotation initially
    }

    public RotatedRectangle(Point2D point1, Point2D point2, double angle) {
        super((int) point1.getX(), (int) point1.getY(), (int) point2.getX() - (int) point1.getX(), (int) point2.getY() - (int) point1.getY());
        this.rotationAngle = Math.toRadians(angle);  // No rotation initially
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    // Set the rotation angle in degrees
    public void setRotation(double degrees) {
        this.rotationAngle = Math.toRadians(degrees);
    }

    // Apply the rotation and draw the rectangle
    public void draw(Graphics2D g2d) {
        // Save the original transform
        AffineTransform originalTransform = g2d.getTransform();

        // Create a new transform for rotating around the center of the rectangle
        AffineTransform transform = new AffineTransform();

        // We need to rotate around the center of the rectangle and then draw it.
        // The center of the rectangle is at (x + width / 2, y + height / 2)
        transform.rotate(rotationAngle, x + (double) width / 2, y + (double) height / 2);
        g2d.transform(transform);
        g2d.draw(this);
    }

    public Area getTransformedArea() {
        AffineTransform transform = new AffineTransform();
        transform.rotate(rotationAngle, x + (double) width / 2, y + (double) height / 2);
        return new Area(transform.createTransformedShape(this));
    }

    public boolean intersects(RotatedRectangle other) {
        Area area1 = this.getTransformedArea();
        Area area2 = other.getTransformedArea();
        area1.intersect(area2);  // Modify area1 to the intersection of both areas
        return !area1.isEmpty(); // Check if there is any overlap
    }
}
