package com.kobot.framework.simulation;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.StubPrimitivesFactory;
import com.kobot.framework.entitysystem.manager.BaseComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.vecmath.Vector3f;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PhysicalObjectTranslationTest {
    private final static float EPSILON = 0.5f;

    private static final float SCALE = 1;

    private static final float MASS = 1;
    private static final float RADIUS = 1;
    private final float SIMULATION_TIME_IN_SEC = 2f;

    private PhysicsSimulator simulator;
    private PhysicalObject sphere;
    private final Vector3f impulseOrForce;

    @Parameterized.Parameters
    public static Collection data() {
        float[] angles = {-10f, -5f, -1f, 0, 1f, 5f, 10f};

        Vector3f[] orientations = new Vector3f[angles.length * angles.length * angles.length];
        int count = 0;
        for (int iX = 0; iX < angles.length; iX++) {
            for (int iY = 0; iY < angles.length; iY++) {
                for (int iZ = 0; iZ < angles.length; iZ++) {
                    orientations[count++] = new Vector3f(angles[iX], angles[iY], angles[iZ]);
                }
            }
        }

        return Arrays.asList(orientations);
    }

    public PhysicalObjectTranslationTest(Vector3f impulseOrForce) {
        this.impulseOrForce = impulseOrForce;
    }

    @Before
    public void setUp() throws Exception {
        sphere = createSphere();
        simulator = new PhysicsSimulator(new Vector3f(), SCALE);
        simulator.add(sphere);
    }

    @Test
    public void testApplyForce(){
        float t = SIMULATION_TIME_IN_SEC;
        final int steps = 60;
        float dt = t / (float)steps;
        for (int step = 1; step <= steps; step++){
            sphere.applyCentralForce(impulseOrForce);
            simulator.stepSimulation(dt);
        }

        // a = F/m
        Vector3f acceleration = new Vector3f(impulseOrForce);
        acceleration.scale(1f / MASS);

        // s = 0.5 * a * t^2
        Vector3f translation = new Vector3f(acceleration);
        translation.scale(0.5f * SIMULATION_TIME_IN_SEC * SIMULATION_TIME_IN_SEC);

        assertTrue("for force = " + impulseOrForce + "expected = " + translation + " actual = " + sphere.getPosition(),
                translation.epsilonEquals(sphere.getPosition(), EPSILON));
    }

    @Test
    public void testApplyImpulse(){
        sphere.applyCentralImpulse(impulseOrForce);

        float t = SIMULATION_TIME_IN_SEC;
        final int steps = 60;
        float dt = t / (float)steps;
        for (int step = 1; step <= steps; step++){
            simulator.stepSimulation(dt);
        }

        // V = impulse/mass
        Vector3f velocity = new Vector3f(impulseOrForce);
        velocity.scale(1f/MASS);

        //s = velocity * time
        Vector3f translation = new Vector3f(velocity);
        translation.scale(SIMULATION_TIME_IN_SEC);

        assertTrue("for impulse = " + impulseOrForce + "expected = " + translation + " actual = " + sphere.getPosition(),
                translation.epsilonEquals(sphere.getPosition(), EPSILON));
    }

    public PhysicalObject createSphere() {
        EntityManager entityManager = new EntityManager();
        StubPrimitivesFactory factory = new StubPrimitivesFactory(entityManager, SCALE);
        Entity sphere = factory.createDynamicSphere(MASS, RADIUS, Color.RED, new Vector3f(0,0,0));
        return new BaseComponentFinder(entityManager).findPhysicalObject(sphere);
    }
}