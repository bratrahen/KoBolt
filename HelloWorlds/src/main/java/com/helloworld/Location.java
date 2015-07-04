package com.helloworld;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.kobot.jbullet.FullObject;
import com.threed.jpct.*;
import com.threed.jpct.util.Light;

import javax.vecmath.Vector3f;

public class Location {
    private DiscreteDynamicsWorld model;
    private World view;
    private Light sun;

    public Location() {
        initModel();
        initView();
    }

    private void initModel() {
        // collision configuration contains default setup for memory, collision
        // setup. Advanced users can create their own configuration.
        CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();

        // use the default collision dispatcher. For parallel processing you
        // can use a diffent dispatcher (see Extras/BulletMultiThreaded)
        CollisionDispatcher dispatcher = new CollisionDispatcher(
                collisionConfiguration);

        // the maximum size of the collision world. Make sure objects stay
        // within these boundaries
        // Don't make the world AABB size too large, it will harm simulation
        // quality and performance
        Vector3f worldAabbMin = new Vector3f(-10000, -10000, -10000);
        Vector3f worldAabbMax = new Vector3f(10000, 10000, 10000);
        int maxProxies = 1024;
        AxisSweep3 overlappingPairCache =
                new AxisSweep3(worldAabbMin, worldAabbMax, maxProxies);
        //BroadphaseInterface overlappingPairCache = new SimpleBroadphase(
        //		maxProxies);

        // the default constraint solver. For parallel processing you can use a
        // different solver (see Extras/BulletMultiThreaded)
        SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();

        model = new DiscreteDynamicsWorld(
                dispatcher, overlappingPairCache, solver,
                collisionConfiguration);

        model.setGravity(new Vector3f(0, -10, 0));
    }

    private void initView() {
        view = new World();
        view.setAmbientLight(30, 30, 30);
        view.getLights().setRGBScale(Lights.RGB_SCALE_2X);
        sun = new Light(view);
        sun.setIntensity(250, 250, 250);
        sun.setAttenuation(800);
        sun.setPosition(new SimpleVector(0, -100, 0
        ));
    }

    public void addObject(FullObject object){
        model.addRigidBody(object.getModel());
        view.addObject(object.getView());
    }

    public World getView() {
        return view;
    }

    public DiscreteDynamicsWorld getModel() {
        return model;
    }
}
