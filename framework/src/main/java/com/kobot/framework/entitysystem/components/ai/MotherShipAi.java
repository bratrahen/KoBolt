package com.kobot.framework.entitysystem.components.ai;

import com.kobot.framework.entitysystem.components.api.AiComponent;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.RangedWeapon;
import com.kobot.framework.entitysystem.components.PhysicsComponent;
import com.kobot.framework.entitysystem.components.Team;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class MotherShipAi implements AiComponent {
    private final EntityManager entityManager;
    private final ComponentFinder finder;

    public MotherShipAi(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.finder = new ComponentFinder(entityManager);
    }

    public void update(float timestepInSeconds) {
        Entity myEntity = finder.findEntitiesForComponent(this).iterator().next();
        Set<Entity> enemies = finder.findEnemies(myEntity);

        if (enemies.isEmpty()){
            return;
        }

        Set<RangedWeapon> myGuns = finder.findRangedWeapons(myEntity);
        for (RangedWeapon gun : myGuns){
            gun.reduceCooldown(timestepInSeconds);
            if (!gun.canFire()){
                return;
            }
            PhysicsComponent myBody = finder.findSinglePhysicalBody(myEntity);
            PhysicsComponent enemyBody = finder.findSinglePhysicalBody(enemies.iterator().next());
            gun.fireAt(myBody.getPosition(), enemyBody.getPosition());
        }
    }
}
