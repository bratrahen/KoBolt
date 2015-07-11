package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.HealthComponent;
import com.kobot.framework.entitysystem.eventbus.EventBus;
import com.kobot.framework.entitysystem.eventbus.GameEvent;
import com.kobot.framework.entitysystem.eventbus.StubListener;
import com.kobot.framework.entitysystem.eventbus.events.RemoveEntityEvent;
import com.kobot.framework.entitysystem.manager.EntityManager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HealthSystemTest {

    @Test
    public void testUpdate() throws Exception {
        EntityManager entityManager = new EntityManager();

        HealthSystem healthSystem = new HealthSystem(entityManager);
        StubListener listener = new StubListener();
        EventBus.addListener(listener, RemoveEntityEvent.class);


        HealthComponent aliveHealth = new HealthComponent(10, 10);
        HealthComponent deadHealth = new HealthComponent(-1, 10);
        entityManager.addComponentToEntity(aliveHealth, new Entity(1));
        entityManager.addComponentToEntity(deadHealth, new Entity(2));

        healthSystem.update(1);

        assertEquals(1, listener.events.size());
        GameEvent event = listener.events.iterator().next();
        assertEquals(RemoveEntityEvent.class, event.getClass());
        assertEquals(2, ((RemoveEntityEvent) event).entity.getId());
    }
}