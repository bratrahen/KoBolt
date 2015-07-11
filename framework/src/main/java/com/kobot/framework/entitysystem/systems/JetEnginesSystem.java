package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.JetEngine;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.simulation.PhysicalObject;

import java.util.Set;

public class JetEnginesSystem extends System {
    public JetEnginesSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(float timeStepInSeconds) {
        Set<JetEngine> allJetEngines = componentFinder.findAllJetEngines();
        for (JetEngine engine : allJetEngines) {
            Entity entity = entityFinder.findEntityForComponent(engine);
            PhysicalObject physicalObject = componentFinder.findPhysicalObject(entity);
            physicalObject.applyCentralForce(engine.getThrust());
        }
    }
}
