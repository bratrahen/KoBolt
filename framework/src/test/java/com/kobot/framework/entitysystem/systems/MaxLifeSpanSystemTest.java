package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.MaxLifeSpan;
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
        MaxLifeSpan maxLifeSpan = new MaxLifeSpan(1.0f);

        manager.addComponentToEntity(maxLifeSpan, entity);

        MaxLifeSpanSystem maxLifeSpanSystem = new MaxLifeSpanSystem(manager);
        maxLifeSpanSystem.update(1.5f);

        Set<Entity> allDisposed = finder.findAllDisposed();
        assertEquals(1, allDisposed.size());
        assertTrue(allDisposed.contains(entity));
    }
}