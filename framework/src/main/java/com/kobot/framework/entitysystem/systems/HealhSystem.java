package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.EntityManager;
import com.kobot.framework.entitysystem.components.HealthComponent;
import com.kobot.framework.entitysystem.components.RendererComponent;
import com.kobot.framework.entitysystem.components.JpctRendererComponent;

import java.util.Collection;

public class HealhSystem extends System{
    public HealhSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(float timeStepInSeconds) {
        Collection<Entity> entities = entityManager.getAllEntitiesPossessingComponentOfClass(HealthComponent.class);

        for (Entity entity : entities) {
            HealthComponent health = (HealthComponent)entityManager.getComponentForEntity(HealthComponent.class, entity);
            RendererComponent renderer = (RendererComponent)entityManager.getComponentForEntity(JpctRendererComponent.class, entity);

            if (!health.isAlive || health.maxHealth <= 0){
                continue;
            }

            if (health.currentHealth <= 0){
                health.isAlive = false;

                if (renderer != null){
                    //renderer
                } else {
                    entityManager.removeEntity(entity);
                }
            }
        }
    }

    void draw(){
        Collection<Entity> entities = entityManager.getAllEntitiesPossessingComponentOfClass(HealthComponent.class);

        for (Entity entity : entities) {
            HealthComponent health = (HealthComponent) entityManager.getComponentForEntity(HealthComponent.class, entity);
            RendererComponent renderer = (RendererComponent) entityManager.getComponentForEntity(JpctRendererComponent.class, entity);

            if (health == null || renderer == null){
                continue;
            }


        }
    }
}
