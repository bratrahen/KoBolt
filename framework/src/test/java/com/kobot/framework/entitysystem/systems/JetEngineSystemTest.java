package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.StubPrimitivesFactory;
import com.kobot.framework.entitysystem.components.JetEngine;
import com.kobot.framework.entitysystem.components.api.Body;
import com.kobot.framework.entitysystem.manager.BaseComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;
import org.junit.Before;
import org.junit.Test;

import javax.vecmath.Vector3f;
import java.awt.*;

import static org.junit.Assert.assertEquals;

public class JetEngineSystemTest {

    private static final float GRAVITY = -10;
    private EntityManager manager;
    private BaseComponentFinder componentFinder;
    private StubPrimitivesFactory factory;
    private PhysicsSystem simulation;

    @Before
    public void setUp() throws Exception {
        manager = new EntityManager();
        componentFinder = new BaseComponentFinder(manager);
        factory = new StubPrimitivesFactory(manager, 1.0f);
        simulation = new PhysicsSystem(manager, GRAVITY, 1.0f);
    }


    @Test
    public void applyForce() {
        Entity sphere = factory.createDynamicSphere(1, 1, Color.RED, new Vector3f(50, 50, 0));
        JetEngine jetEngine = new JetEngine(Math.abs(GRAVITY));
        manager.addComponentToEntity(jetEngine, sphere);
        jetEngine.setThrustDirection(new Vector3f(0, -1, 0));
        JetEnginesSystem enginesSystem = new JetEnginesSystem(manager);

        float dt = 2.0f / 100.0f;
        for (int i = 1; i < 100; i++) {
            enginesSystem.update(dt);
            simulation.update(dt);
        }

        Body body = componentFinder.findPhysicalObject(sphere);
        assertEquals(new Vector3f(50, 50, 0), body.getPosition());
    }
}