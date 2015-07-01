package com.kobot.framework;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.kobot.framework.objects.physics.GameObject;
import com.kobot.framework.objects.graphic.common.GameObjectFactory;
import com.kobot.framework.objects.graphic.common.Display;

import javax.vecmath.Vector3f;

public class Scene {
    private final GameObjectFactory factory;
    private final Display display;
    private final DynamicsWorld simulation;

    public Scene(GameObjectFactory factory, Display display) {
        this.factory = factory;
        this.display = display;
        this.simulation = createSimulation();
    }

    protected DynamicsWorld createSimulation() {
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

        DiscreteDynamicsWorld result = new DiscreteDynamicsWorld(
                dispatcher, overlappingPairCache, solver,
                collisionConfiguration);

        result.setGravity(new Vector3f(0, -10, 0));

        return result;
    }

    public void addObject(GameObject object){
        display.add(object);
        simulation.addRigidBody(object.getRigidBody());
    }

    public void stepSimulation(float timestepInSecond) {
        simulation.stepSimulation(timestepInSecond, 10);
    }
    public void render(){
        display.render();}
}
