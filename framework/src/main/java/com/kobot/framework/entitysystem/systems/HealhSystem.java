package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.HealthComponent;

import java.util.Set;

public class HealhSystem extends System{
    public HealhSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(float timeStepInSeconds) {
        Set<Entity> entities = finder.getAllEntitiesPossessingComponentOfClass(HealthComponent.class);
//
//        for (Entity entity : entities) {
//            HealthComponent health = (HealthComponent)finder.getComponentsForEntity(HealthComponent.class, entity);
//            RendererComponent renderer = (RendererComponent)entityManager.getComponentsForEntity(JpctRendererComponent.class, entity);
//
//            if (!health.isAlive || health.maxHealth <= 0){
//                continue;
//            }
//
//            if (health.currentHealth <= 0){
//                health.isAlive = false;
//
//                if (renderer != null){
//                    //renderer
//                } else {
//                    entityManager.removeEntity(entity);
//                }
//            }
//        }
    }

    void draw(){
//        Collection<Entity> entities = entityManager.getAllEntitiesPossessingComponentOfClass(HealthComponent.class);
//
//        for (Entity entity : entities) {
//            HealthComponent health = (HealthComponent) entityManager.getComponentsForEntity(HealthComponent.class, entity);
//            RendererComponent renderer = (RendererComponent) entityManager.getComponentsForEntity(JpctRendererComponent.class, entity);
//
//            if (health == null || renderer == null){
//                continue;
//            }
//
//
//        }
    }
}
