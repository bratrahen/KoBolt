package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.manager.BaseComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;

public abstract class System {
    final protected EntityManager entityManager;
    final protected BaseComponentFinder componentFinder;
    final protected EntityFinder entityFinder;

    public System(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.componentFinder = new BaseComponentFinder(entityManager);
        this.entityFinder = new EntityFinder(entityManager);
    }

    public abstract void update(float timeStepInSeconds);
}
