package com.kobot.framework.entitysystem.components.ai;

import com.kobot.framework.entitysystem.components.api.Body;
import com.kobot.framework.entitysystem.components.api.AiComponent;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.manager.EntityFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.RangedWeapon;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;
import java.util.Set;

public class MotherShipAi implements AiComponent {
    private final ComponentFinder componentFinder;
    private final EntityFinder entityFinder;

    public MotherShipAi(EntityManager entityManager) {
        this.componentFinder = new ComponentFinder(entityManager);
        this.entityFinder = new EntityFinder(entityManager);
    }

    public void update(float timestepInSeconds) {
        Set<Entity> enemies = entityFinder.findEnemies(getEntity());
        if (enemies.isEmpty()){
            return;
        }

        Entity target = enemies.iterator().next();

        for (RangedWeapon gun : getRangedWeapons()){
            gun.reduceCooldown(timestepInSeconds);
            if (!gun.canFire()){
                continue;
            }

            Body enemyBody = componentFinder.findPhysicalObject(target);
            Vector3f start = getBody().getPosition();
            start.add(new Vector3f(0, 10 , 0));
            gun.fire(start, enemyBody.getPosition());
        }
    }

    @NotNull
    private Body getBody() {
        return componentFinder.findPhysicalObject(getEntity());
    }

    @NotNull
    private Entity getEntity() {
        return entityFinder.findEntityForComponent(this);
    }

    @NotNull
    private Set<RangedWeapon> getRangedWeapons() {
        return componentFinder.findRangedWeapons(getEntity());
    }
}
