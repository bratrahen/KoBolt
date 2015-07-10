package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.Dispose;
import com.kobot.framework.entitysystem.manager.EntityManager;

import java.util.Set;

public class DisposeSystem extends System {
    public DisposeSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(float timeStepInSeconds) {
        Set<Entity> entities = entityFinder.findAllEntitiesPossessingComponentOfClass(Dispose.class);
        for (Entity entity : entities) {
            entityManager.removeEntity(entity);
        }
    }
}
