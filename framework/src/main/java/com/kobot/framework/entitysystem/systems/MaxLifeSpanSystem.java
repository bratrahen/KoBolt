package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.MaxLifeSpan;
import com.kobot.framework.entitysystem.eventbus.EventBus;
import com.kobot.framework.entitysystem.eventbus.events.RemoveEntityEvent;
import com.kobot.framework.entitysystem.manager.EntityManager;

public class MaxLifeSpanSystem extends System {
    public MaxLifeSpanSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(float timeStepInSeconds) {
        ;
        for (MaxLifeSpan maxLifeSpan : componentFinder.findAllMaxLifeSpans()) {
            maxLifeSpan.update(timeStepInSeconds);

            if (maxLifeSpan.shouldDie()) {
                Entity entity = entityFinder.findEntityForComponent(maxLifeSpan);
                EventBus.raiseEvent(new RemoveEntityEvent(entity));
            }
        }
    }
}
