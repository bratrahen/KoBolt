package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.EntityManager;

public abstract class System {
    final protected EntityManager entityManager;

    public System(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract void update(float timeStepInSeconds);
}
