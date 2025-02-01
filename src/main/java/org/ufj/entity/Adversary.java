package org.ufj.entity;

import org.ufj.util.DifficultyLevel;
import org.ufj.util.Point2D;

import java.util.List;

public class Adversary {
    private Character character;
    protected DifficultyLevel level;
    private final List<Point2D> checkpoints;
    private int currentCheckpointIndex;

    public Adversary(DifficultyLevel level, List<Point2D> checkpoints, Character character) {
        this.level = level;
        this.character = character;
        this.currentCheckpointIndex = 0;
        this.checkpoints = checkpoints;
    }

    public void update() {
        // TODO: AI logic
        if (currentCheckpointIndex < checkpoints.size()) {
            Point2D target = checkpoints.get(currentCheckpointIndex);
            moveToCheckpoint(target);
            if (character.getPosition().equals(target)) {
                currentCheckpointIndex++;
            }
        }
    }

    private void moveToCheckpoint(Point2D target) {
        // Movement logic based on difficulty level
        switch (level) {
            case EASY:
                character.setMaxSpeed((int) (0.5 * character.getMaxSpeed()));
                break;
            case MEDIUM:
                character.setMaxSpeed((int) (0.9 * character.getMaxSpeed()));
                break;
            case HARD:
                character.setMaxSpeed((int) (1.1 * character.getMaxSpeed()));
                break;
        }
        // Movement logic to the next checkpoint
        moveTowards(target);
    }

    @SuppressWarnings("EmptyMethod")
    private void moveTowards(Point2D target) {
        // Movement logic
        // ...
    }

    public Character getCharacter() {
        return character;
    }

    public DifficultyLevel getLevel() {
        return level;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setLevel(DifficultyLevel level) {
        this.level = level;
    }
}
