package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.eventbus.EventBus;
import com.kobot.framework.entitysystem.eventbus.GameEvent;
import com.kobot.framework.entitysystem.eventbus.GameEventListener;
import com.kobot.framework.entitysystem.eventbus.events.CreateEntityEvent;
import com.kobot.framework.entitysystem.eventbus.events.RemoveEntityEvent;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.simulation.PhysicsSimulator;
import com.kobot.framework.simulation.RayCaster;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.vecmath.Vector3f;
import java.util.HashSet;
import java.util.Set;

public class PhysicsSystem extends System implements GameEventListener {
    private PhysicsSimulator simulation;
    private Set<GameEvent> eventQueue = new HashSet<GameEvent>();

    public PhysicsSystem(EntityManager manager, float gravity, float scale) {
        this(manager, new Vector3f(0, gravity, 0), scale);
    }

    public PhysicsSystem(EntityManager manager, Vector3f gravity, float scale) {
        super(manager);
        simulation = new PhysicsSimulator(gravity, scale);

        EventBus.addListener(this, CreateEntityEvent.class);
        EventBus.addListener(this, RemoveEntityEvent.class);
    }

    @Override
    public void update(float timestepInSecond) {
        processEventQueue();
        simulation.stepSimulation(timestepInSecond);
    }

    private void processEventQueue() {
        for (GameEvent event : eventQueue) {
            if (event instanceof CreateEntityEvent){
                add(((CreateEntityEvent) event).entity);
            } else if (event instanceof RemoveEntityEvent){
                remove(((RemoveEntityEvent) event).entity);
            } else {
                throw new NotImplementedException();
            }
        }
        eventQueue.clear();
    }

    private void add(Entity entity) {
        simulation.add(componentFinder.findPhysicalObject(entity));
    }

    private void remove(Entity entity) {
        simulation.remove(componentFinder.findPhysicalObject(entity));
    }

    public RayCaster createRayCaster() {
        return simulation.createRayCaster();
    }

    public void handle(GameEvent event) {
        eventQueue.add(event);
    }
}