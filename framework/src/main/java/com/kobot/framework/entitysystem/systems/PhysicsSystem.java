package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.api.Body;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.simulation.PhysicsSimulator;
import com.kobot.framework.simulation.RayCaster;

import javax.vecmath.Vector3f;
import java.util.HashSet;
import java.util.Set;

public class PhysicsSystem extends System {
    Set<Entity> simulatedEntities = new HashSet<Entity>();
    PhysicsSimulator simulation;

    public PhysicsSystem(EntityManager manager, float gravity, float scale) {
        this(manager, new Vector3f(0, gravity, 0), scale);
    }

    public PhysicsSystem(EntityManager manager, Vector3f gravity, float scale) {
        super(manager);
        simulation = new PhysicsSimulator(gravity, scale);
    }

    @Override
    public void update(float timestepInSecond) {
        Set<Entity> entities = entityFinder.findAllEntitiesPossessingComponentOfClass(Body.class);

        for (Entity entity : entities) {
            if (!simulatedEntities.contains(entity)) {
                add(entity);
            }
        }

        for (Entity entity : componentFinder.findAllDisposed()) {
            remove(entity);
        }

        simulation.stepSimulation(timestepInSecond);
    }

    private void add(Entity entity) {
        simulation.add(componentFinder.findPhysicalObject(entity));
        simulatedEntities.add(entity);
    }

    private void remove(Entity entity) {
        simulation.remove(componentFinder.findPhysicalObject(entity));
        simulatedEntities.remove(entity);
    }

    public RayCaster createRayCaster() {
        return simulation.createRayCaster();
    }
}