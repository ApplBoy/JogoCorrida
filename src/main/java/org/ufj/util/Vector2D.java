package org.ufj.util;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Create a unit vector from an angle (in degrees)
    public static Vector2D fromAngle(double angle) {
        double radians = Math.toRadians(angle);
        return new Vector2D(Math.cos(radians), Math.sin(radians));
    }

    public String toString() {
        return String.format("(%f, %f)", this.x, this.y);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Vector2D other) {
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector2D copy() {
        return new Vector2D(this.x, this.y);
    }

    public Vector2D clone() {
        try {
            Vector2D clone = (Vector2D) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return this.copy();
    }
}
