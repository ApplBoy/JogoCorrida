package org.ufj.util;

import static java.lang.Math.abs;

public class Point2D {
    private double x;
    private double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D(Point2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point2D add(Point2D other) {
        return new Point2D(this.x + other.x, this.y + other.y);
    }

    public Point2D subtract(Point2D other) {
        return new Point2D(this.x - other.x, this.y - other.y);
    }

    public double distance(Point2D other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public boolean isClose(Point2D other, double distanceThreshold) {
        return this.distance(other) < distanceThreshold;
    }

    public boolean equals(Point2D other) {
        return this.x == other.x && this.y == other.y;
    }

    public boolean isEachPointClose(Point2D other, double distanceThreshold) {
        return abs(getX() - other.getX()) < distanceThreshold || abs(getY() - other.getY()) < distanceThreshold;
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void move(Point2D other) {
        this.x += other.getX();
        this.y += other.getY();
    }

    public java.awt.geom.Point2D abstractPoint() {
        return new java.awt.geom.Point2D.Double(x, y);
    }

    @Override
    public String toString() {
        return "Point2D{" + "x=" + x + ", y=" + y + '}';
    }
}