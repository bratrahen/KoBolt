package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.MaxLifeSpan;
import com.kobot.framework.entitysystem.eventbus.EventBus;
import com.kobot.framework.entitysystem.eventbus.GameEvent;
import com.kobot.framework.entitysystem.eventbus.StubListener;
import com.kobot.framework.entitysystem.eventbus.events.RemoveEntityEvent;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.Set;


public class MaxLifeSpanSystemTest {

    @Test
    public void update_() throws Exception {
        EntityManager manager = new EntityManager();
        ComponentFinder finder = new ComponentFinder(manager);

        Entity entity = new Entity(1);
        manager.addComponentToEntity(new MaxLifeSpan(1.0f), entity);

        StubListener listener = new StubListener();
        EventBus.addListener(listener, RemoveEntityEvent.class);

        MaxLifeSpanSystem maxLifeSpanSystem = new MaxLifeSpanSystem(manager);
        maxLifeSpanSystem.update(1.5f);

        assertEquals(1, listener.events.size());

        GameEvent event = listener.events.iterator().next();
        assertEquals(RemoveEntityEvent.class, event.getClass());
    }
}