package com.test.kobot.object;

import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import com.test.kobot.StubFactory;
import com.test.kobot.TestGame;
import org.junit.Test;

import javax.vecmath.Vector3f;

import static org.junit.Assert.*;

public class SphereTest {
    private static final double DELTA = 0.0001;
    public static final float MASS_IN_KG = 1;
    public static final float RADIUS_IN_M = 1;

    @Test
    public void staticBall_getPosition(){
        TestGame game = new TestGame();
        game.maxGameLoop = 1;
        game.tickInMillis = game.TIMESTEP_IN_MILLIS;
        game.simulation = StubFactory.createTestDynamicWorld();

        final Sphere sphere = new Sphere(0.0f, RADIUS_IN_M, new Vector3f(1, 20, 300), new DefaultMotionState());
        game.run();

        assertEquals(1.0, sphere.getPosition().x, DELTA);
        assertEquals(20.0, sphere.getPosition().y, DELTA);
        assertEquals(300.0, sphere.getPosition().z, DELTA);
    }

    @Test
    public void fall_tickEqualsTime(){
        TestGame game = new TestGame();
        game.maxGameLoop = 1;
        game.tickInMillis = game.TIMESTEP_IN_MILLIS;
        game.simulation = StubFactory.createTestDynamicWorld();

        final Sphere sphere = new Sphere(MASS_IN_KG, RADIUS_IN_M, new Vector3f(0, 0, 0), new DefaultMotionState());
        game.simulation.addRigidBody(sphere.getRigidBody());

        game.run();

        assertEquals(0.0, sphere.getPosition().x, DELTA);
        assertEquals(0.0, sphere.getPosition().z, DELTA);

        Transform trans = new Transform();
        sphere.getRigidBody().getWorldTransform(trans);
        assertEquals(sphere.getPosition().y, trans.origin.y, DELTA);

        double a = StubFactory.GRAVITY;
        double t2 = game.simulationTimeInSeconds * game.simulationTimeInSeconds;
        double s = a * t2;// / 2.0;
        assertEquals(s, trans.origin.y, DELTA);

    }
}