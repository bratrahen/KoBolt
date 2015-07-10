package com.kobot.framework.entitysystem.components;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.StubPrimitivesFactory;
import com.kobot.framework.entitysystem.components.api.Body;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.systems.PhysicsSystem;
import org.junit.Before;
import org.junit.Test;

import javax.vecmath.Vector3f;
import java.awt.*;

import static org.junit.Assert.*;

public class JetEngineTest {

    private EntityManager manager;
    private ComponentFinder componentFinder;
    private StubPrimitivesFactory factory;
    private PhysicsSystem simulation;

    private static final float GRAVITY = -10;
    private EntityFinder entityFinder;

    @Before
    public void setUp() throws Exception {
        manager = new EntityManager();
        componentFinder = new ComponentFinder(manager);
        entityFinder = new EntityFinder(manager);
        factory = new StubPrimitivesFactory(manager);
        simulation = new PhysicsSystem(manager, GRAVITY, 1.0f);
    }


    @Test
    public void applyForce(){
        Entity sphere = factory.createDynamicSphere(1, 1, Color.RED, new Vector3f(50, 50, 0));

        JetEngine jetEngine = new JetEngine(componentFinder, entityFinder, new Vector3f(0, -GRAVITY, 0));
        manager.addComponentToEntity(jetEngine, sphere);

        jetEngine.setThrustPercentage(100);

        float dt = 2.0f/100.0f;
        for(int i = 1; i < 100; i++ ){
            jetEngine.applyForce();
            simulation.update(dt);
        }

        Body body = componentFinder.findPhysicalObject(sphere);
        assertEquals(new Vector3f(50, 50, 0), body.getPosition());
    }
}