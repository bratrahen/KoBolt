package com.kobot.framework.simulation;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.api.Body;

import javax.media.j3d.PhysicalBody;
import javax.vecmath.Vector3f;
import java.util.Set;

/**
 *  By default, Bullet assumes units to be in meters and time in seconds.
 *  Moving objects are assumed to be in the range of 0.05 units, about the size of a pebble, to 10, the size of a truck.
 *  If your objects are much bigger, and you use the default gravity, the simulation will appear in slow-motion.
 *  Try to scale down the objects to around 1 in meter units first.
 *  By scaling the world, you change dimensions and velocities appropriately so that they are back within the range
 *  that Bullet was designed for (0.05 to 10). Thus the simulation becomes more realistic.
 *  @see <a href="http://bulletphysics.org/mediawiki-1.5.8/index.php/Scaling_The_World">Scaling The World</a>
 *
 */
public class PhysicsSimulator {
    private DynamicsWorld simulation;

    public PhysicsSimulator(Vector3f gravity) {
        simulation = createSimulation(gravity);
    }

    private static DynamicsWorld createSimulation(Vector3f gravity) {
        // collision configuration contains default setup for memory, collision
        // setup. Advanced users can getById their own configuration.
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

        result.setGravity(gravity);

        return result;
    }

    public void add(PhysicalObject object){
        simulation.addRigidBody(object.getRigidBody());
    }

    public void remove(PhysicalObject object){
        simulation.removeRigidBody(object.getRigidBody());
    }

    /**
     * @param timestep measured in seconds [s]
     */
    public void stepSimulation(float timestep) {
        simulation.stepSimulation(timestep, 1);
    }

    public Vector3f getGravity(){
        Vector3f result = new Vector3f();
        return simulation.getGravity(result);
    }


}
