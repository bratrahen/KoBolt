package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.eventbus.EventBus;
import com.kobot.framework.entitysystem.eventbus.GameEvent;
import com.kobot.framework.entitysystem.eventbus.GameEventListener;
import com.kobot.framework.entitysystem.eventbus.events.RemoveEntityEvent;
import com.kobot.framework.entitysystem.manager.EntityManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashSet;
import java.util.Set;

public class RemoveEntitySystem extends System implements GameEventListener{

    Set<RemoveEntityEvent> eventQueue = new HashSet<RemoveEntityEvent>();

    public RemoveEntitySystem(EntityManager entityManager) {
        super(entityManager);
        EventBus.addListener(this, RemoveEntityEvent.class);
    }

    @Override
    public void update(float timeStepInSeconds) {
        for (RemoveEntityEvent event : eventQueue) {
            entityManager.removeEntity(event.entity);
        }
        eventQueue.clear();
    }

    public void handle(GameEvent event) {
        if (event instanceof RemoveEntityEvent){
            eventQueue.add((RemoveEntityEvent) event);
        } else {
            throw new NotImplementedException();
        }
    }
}
