package com.kobot.framework.entitysystem.systems;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.api.Body;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.JpctRendererComponent;

import javax.vecmath.Vector3f;
import java.util.HashSet;
import java.util.Set;

public class PhysicsSystem extends System {

    Set<Entity> simulatedEntities = new HashSet<Entity>();
    private DynamicsWorld simulation;
    public final float gravity;

    public PhysicsSystem(EntityManager entityManager) {
        this(entityManager, -10.0f);
    }

    public PhysicsSystem(EntityManager manager, float gravity) {
        super(manager);
        this.gravity = gravity;
        this.simulation = createSimulation(gravity);
    }

    public static DynamicsWorld createSimulation(float gravity) {
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

        result.setGravity(new Vector3f(0, gravity, 0));

        return result;
    }

    @Override
    public void update(float timestepInSecond) {
        Set<Entity> entities = finder.findAllEntitiesPossessingComponentOfClass(Body.class);

        for (Entity entity : entities) {
            if (!simulatedEntities.contains(entity)) {
                add(entity);
            }
        }

        for (Entity entity : finder.findAllDisposed()) {
            remove(entity);
        }

        simulation.stepSimulation(timestepInSecond, 1);
    }

    private void remove(Entity entity) {
        Body component = finder.findPhysicalBody(entity);
        component.removeFromSimulation(simulation);
        simulatedEntities.remove(entity);
    }

    private void add(Entity entity) {
        Body component = finder.findPhysicalBody(entity);
        component.addToSimulation(simulation);
        simulatedEntities.add(entity);
    }
}