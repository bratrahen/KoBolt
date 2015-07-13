package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.StubPrimitivesFactory;
import com.kobot.framework.entitysystem.eventbus.EventBus;
import com.kobot.framework.entitysystem.eventbus.events.RemoveEntityEvent;
import com.kobot.framework.entitysystem.manager.EntityFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.simulation.PhysicalObject;
import org.junit.Test;

import javax.vecmath.Vector3f;
import java.awt.*;
import java.util.Set;

import static org.junit.Assert.*;

public class RemoveEntitySystemTest {
    @Test
    public void test(){
        EntityManager entityManager = new EntityManager();
        EntityFinder entityFinder = new EntityFinder(entityManager);

        StubPrimitivesFactory factory = new StubPrimitivesFactory(entityManager, 1.0f);
        Entity sphere = factory.createStaticSphere(1, Color.GREEN, new Vector3f());

        RemoveEntitySystem removeEntitySystem = new RemoveEntitySystem(entityManager);
        EventBus.raiseEvent(new RemoveEntityEvent(sphere));
        removeEntitySystem.update(1);

        Set<Entity> entities = entityFinder.findAllEntitiesPossessingComponentOfClass(PhysicalObject.class);
        assertEquals(0, entities.size());
    }
}