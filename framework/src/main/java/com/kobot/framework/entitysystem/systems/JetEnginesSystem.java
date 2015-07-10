package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.components.JetEngine;
import com.kobot.framework.entitysystem.manager.EntityManager;

import java.util.Set;

public class JetEnginesSystem extends System {
    public JetEnginesSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(float timeStepInSeconds) {
        Set<JetEngine> allJetEngines = componentFinder.findAllJetEngines();
        for (JetEngine engine : allJetEngines) {
            engine.applyForce();
        }
    }
}
