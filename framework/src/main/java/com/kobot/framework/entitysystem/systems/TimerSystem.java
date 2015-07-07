package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.components.api.modifiers.Timeable;
import com.kobot.framework.entitysystem.manager.EntityManager;

import java.util.Set;

public class TimerSystem extends System {
    public TimerSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(float timeStepInSeconds) {
        Set<Timeable> allTimeable = finder.findAllTimeable();
        for (Timeable timeable : allTimeable) {
            timeable.update(timeStepInSeconds);
        }
    }
}
