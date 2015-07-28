package com.kobot.framework.simulation;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.StubPrimitivesFactory;
import com.kobot.framework.entitysystem.manager.BaseComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;
import org.junit.Test;

import javax.vecmath.Vector3f;
import java.awt.*;

import static org.junit.Assert.*;

public class PhysicsSimulatorTest {
    final static float DELTA = 0.1f;

    @Test
    public void testStepSimulation() throws Exception {
        final float SCALE = 1;
        final Vector3f GRAVITY = new Vector3f(0, -10, 0);
        final float MASS = 1;
        final float RADIUS = 1;
        final Vector3f START_POSITION = new Vector3f(0, 100, 0);
        EntityManager entityManager = new EntityManager();
        StubPrimitivesFactory factory = new StubPrimitivesFactory(entityManager, SCALE);

        Entity sphere = factory.createDynamicSphere(MASS, RADIUS, Color.RED, START_POSITION);
        PhysicalObject physicalSphere = new BaseComponentFinder(entityManager).findPhysicalObject(sphere);

        PhysicsSimulator simulator = new PhysicsSimulator(GRAVITY, SCALE);
        simulator.add(physicalSphere);

        float t = 1;
        final int steps = 60;
        float dt = t / (float)steps;
        for (int step = 1; step <= steps; step++){
            simulator.stepSimulation(dt);
        }

        float s = 0.5f * GRAVITY.y * t * t;
        float y = START_POSITION.y + s;
        assertEquals(y, physicalSphere.getPosition().y, DELTA);
    }
}