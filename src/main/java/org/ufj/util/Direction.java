package org.ufj.util;

public enum Direction {
    UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT;

    public static Direction getDirection(double angle) {
        if (angle >= 0 && angle < 45) {
            return Direction.DOWN_RIGHT;
        } else if (angle >= 45 && angle < 90) {
            return Direction.DOWN;
        } else if (angle >= 90 && angle < 135) {
            return Direction.DOWN_LEFT;
        } else if (angle >= 135 && angle < 180) {
            return Direction.LEFT;
        } else if (angle >= 180 && angle < 225) {
            return Direction.UP_LEFT;
        } else if (angle >= 225 && angle < 270) {
            return Direction.UP;
        } else if (angle >= 270 && angle < 315) {
            return Direction.UP_RIGHT;
        } else {
            return Direction.RIGHT;
        }
    }
}