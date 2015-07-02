package com.kobot.framework.entitysystem.systems;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.EntityManager;
import com.kobot.framework.entitysystem.components.JpctRendererComponent;
import com.kobot.framework.entitysystem.components.PhysicsComponent;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PhysicsSystem extends System {

    Set<Entity> simulatedEntities = new HashSet<Entity>();
    private DynamicsWorld simulation;

    public PhysicsSystem(EntityManager entityManager) {
        super(entityManager);
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

    @Override
    public void update(float timestepInSecond) {
        Collection<Entity> entities = entityManager.getAllEntitiesPossessingComponentOfClass(JpctRendererComponent.class);
        for (Entity entity : entities) {
            if (!simulatedEntities.contains(entity)) {
                add(entity);
            }
        }

        HashSet<Entity> entitiesToBeRemoved = getEntitiesNoLongerSimulated(entities);
        for (Entity entity : entitiesToBeRemoved) {
            remove(entity);
        }

        simulation.stepSimulation(timestepInSecond, 1);
    }

    private void remove(Entity entity) {
        PhysicsComponent component = (PhysicsComponent) entityManager.getComponentForEntity(PhysicsComponent.class, entity);
        simulation.removeRigidBody(component.body);
        simulatedEntities.remove(entity);
    }

    @NotNull
    private HashSet<Entity> getEntitiesNoLongerSimulated(Collection<Entity> entities) {
        HashSet<Entity> result = new HashSet<Entity>(simulatedEntities);
        result.removeAll(entities);
        return result;
    }

    private void add(Entity entity) {
        PhysicsComponent component = (PhysicsComponent) entityManager.getComponentForEntity(PhysicsComponent.class, entity);
        simulation.addRigidBody(component.body);
        simulatedEntities.add(entity);
    }
}