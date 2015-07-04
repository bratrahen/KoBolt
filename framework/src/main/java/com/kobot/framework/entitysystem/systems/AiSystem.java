package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.ai.MotherShipAi;

public class AiSystem extends System {
    public AiSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(float timeStepInSeconds) {
        for (MotherShipAi ai : finder.findAllMotherShipsAi()) {
            ai.update(timeStepInSeconds);
        }
    }
}
