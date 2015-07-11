package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.StubPrimitivesFactory;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.simulation.PhysicalObject;
import org.junit.Test;

import javax.vecmath.Vector3f;
import java.awt.*;

import static org.junit.Assert.*;

public class PhysicsSystemTest {

    @Test
    public void createEvent(){
        EntityManager entityManager = new EntityManager();
        StubPrimitivesFactory factory = new StubPrimitivesFactory(entityManager);
        PhysicsSystem physicsSystem = new PhysicsSystem(entityManager, new Vector3f(0, -10, 0), 1f);

        Entity sphere = factory.createStaticSphere(1, Color.RED, new Vector3f());
        physicsSystem.update(1f);

        PhysicalObject object = new ComponentFinder(entityManager).findPhysicalObject(sphere);
        assertTrue(object.isInWorld());
    }
}