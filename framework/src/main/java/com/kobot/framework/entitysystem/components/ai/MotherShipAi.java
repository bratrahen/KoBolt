package com.kobot.framework.entitysystem.components.ai;

import com.kobot.framework.entitysystem.components.api.AiComponent;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.RangedWeapon;
import com.kobot.framework.entitysystem.components.PhysicsComponent;

import javax.vecmath.Vector3f;
import java.util.Set;

public class MotherShipAi implements AiComponent {
    private final ComponentFinder finder;

    public MotherShipAi(EntityManager entityManager) {
        this.finder = new ComponentFinder(entityManager);
    }

    public void update(float timestepInSeconds) {
        Entity myEntity = finder.findEntityForComponent(this);
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
            PhysicsComponent myBody = finder.findPhysicalBody(myEntity);
            PhysicsComponent enemyBody = finder.findPhysicalBody(enemies.iterator().next());
            Vector3f start = myBody.getPosition();
            start.add(new Vector3f(0, 10 , 0));
            gun.fireAt(start, enemyBody.getPosition());
        }
    }
}
