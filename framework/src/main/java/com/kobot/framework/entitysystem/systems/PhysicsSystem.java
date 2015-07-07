package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.api.Body;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.simulation.PhysicsSimulator;

import javax.vecmath.Vector3f;
import java.util.HashSet;
import java.util.Set;

public class PhysicsSystem extends System {
    Set<Entity> simulatedEntities = new HashSet<Entity>();
    PhysicsSimulator simulation;

    public PhysicsSystem(EntityManager entityManager) {
        this(entityManager, -10.0f);
    }

    public PhysicsSystem(EntityManager manager, float gravity) {
        this(manager, new Vector3f(0, gravity, 0));
    }

    public PhysicsSystem(EntityManager manager, Vector3f gravity) {
        super(manager);
        simulation = new PhysicsSimulator(gravity);
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

        simulation.stepSimulation(timestepInSecond);
    }

    private void remove(Entity entity) {
        simulation.remove(finder.findPhysicalObject(entity));
        simulatedEntities.remove(entity);
    }

    private void add(Entity entity) {
        simulation.add(finder.findPhysicalObject(entity));
        simulatedEntities.add(entity);
    }
}