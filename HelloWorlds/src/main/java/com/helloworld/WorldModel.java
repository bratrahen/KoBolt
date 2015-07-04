package com.helloworld;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.kobot.objects.GameObject;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.Collection;

public class WorldModel {
    WorldView worldView;
    Collection<GameObject> objects = new ArrayList<GameObject>();
    private final DiscreteDynamicsWorld dynamicsWorld;


    public WorldModel(WorldView worldView){
        this.worldView = worldView;

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

        dynamicsWorld = new DiscreteDynamicsWorld(
                dispatcher, overlappingPairCache, solver,
                collisionConfiguration);

        dynamicsWorld.setGravity(new Vector3f(0, -10, 0));
    }

    public void update(long ticks) {
        dynamicsWorld.stepSimulation(1.f / 60.f, 10);
    }

    public void addObject(GameObject object){
        objects.add(object);
        dynamicsWorld.addRigidBody(object);
        worldView.addObject(object);
    }
}
