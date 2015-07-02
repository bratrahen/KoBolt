package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.ComponentFinder;
import com.kobot.framework.entitysystem.EntityManager;

public abstract class System {
    final protected EntityManager entityManager;
    final protected ComponentFinder finder;

    public System(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.finder = new ComponentFinder(entityManager);
    }

    public abstract void update(float timeStepInSeconds);
}
