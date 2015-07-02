package com.kobot.framework.entitysystem;

public class Entity {
    long entityId;

    public Entity(long entityId) {
        this.entityId = entityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity entity = (Entity) o;

        return entityId == entity.entityId;
    }

    @Override
    public int hashCode() {
        return (int) (entityId ^ (entityId >>> 32));
    }

    public long getId() {
        return entityId;
    }
}
