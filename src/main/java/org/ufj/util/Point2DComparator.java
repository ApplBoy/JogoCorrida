package org.ufj.util;

import java.util.Comparator;

public class Point2DComparator implements Comparator<Point2D> {
    @Override
    public int compare(Point2D p1, Point2D p2) {
        // First compare by x coordinate
        int result = Double.compare(p1.getX(), p2.getX());
        // If they are equal, compare by y coordinate
        if (result == 0) {
            result = Double.compare(p1.getY(), p2.getY());
        }
        return result;
    }
}
