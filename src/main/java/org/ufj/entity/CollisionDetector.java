package org.ufj.entity;

import org.ufj.tile.MapLimits;
import org.ufj.util.Point2D;

public class CollisionDetector {
    private final Entity[] entities;
    private final MapLimits mapLimits;

    public CollisionDetector(Entity[] entities, MapLimits mapLimits) {
        this.entities = entities;
        this.mapLimits = mapLimits;
    }

    public static boolean checkCollision(Entity entity1, Entity entity2) {
        if (entity1 == null || entity2 == null) {
            return false;
        } else if (entity1.getBounds() == null || entity2.getBounds() == null) {
            return false;
        } else if (entity1.isSolid() && entity2.isSolid()) {
            return entity1.getBounds().intersects(entity2.getBounds());
        } else {
            return false;
        }
    }

    public boolean iterateCollisions(Entity entity) {
        for (Entity other : entities) {
            if (!entity.getName().equals(other.getName()) && checkCollision(entity, other)) {
                return true;
            }
        }
        // Check if it's inside the map limits
        return !mapLimits.isWithinBounds(entity.getBounds());
    }

    public boolean iterateCollisions(Entity entity, Point2D position) {
        // Copy the entity and move it to the new position
        Entity temp = new Entity(entity);
        temp.setPosition(position);
        return iterateCollisions(temp);
    }

    @Override
    public String toString() {
        StringBuilder EntityList = new StringBuilder();
        for (Entity entity : entities) {
            EntityList.append(entity.toString()).append("\n");
        }
        return "CollisionDetector {\n" +
                "entities=\n" + EntityList +
                '}';
    }
}
