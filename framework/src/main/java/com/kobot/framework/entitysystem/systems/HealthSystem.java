package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.eventbus.EventBus;
import com.kobot.framework.entitysystem.eventbus.events.RemoveEntityEvent;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.HealthComponent;

import java.util.Set;

public class HealthSystem extends System{
    public HealthSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(float timeStepInSeconds) {
        Set<HealthComponent> allHealth = componentFinder.findAllHealth();
        for (HealthComponent health : allHealth) {
            if (health.currentHealth <= 0){
                Entity entity = entityFinder.findEntityForComponent(health);
                EventBus.raiseEvent(new RemoveEntityEvent(entity));
            }
        }
    }
}
