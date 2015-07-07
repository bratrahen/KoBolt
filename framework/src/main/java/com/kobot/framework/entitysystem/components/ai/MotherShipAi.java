package com.kobot.framework.entitysystem.components.ai;

import com.kobot.framework.entitysystem.components.api.Body;
import com.kobot.framework.entitysystem.components.api.AiComponent;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.RangedWeapon;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;
import java.util.Set;

public class MotherShipAi implements AiComponent {
    private final ComponentFinder finder;

    public MotherShipAi(EntityManager entityManager) {
        this.finder = new ComponentFinder(entityManager);
    }

    public void update(float timestepInSeconds) {
        Set<Entity> enemies = finder.findEnemies(getEntity());
        if (enemies.isEmpty()){
            return;
        }

        Entity target = enemies.iterator().next();

        for (RangedWeapon gun : getRangedWeapons()){
            gun.reduceCooldown(timestepInSeconds);
            if (!gun.canFire()){
                continue;
            }

            Body enemyBody = finder.findPhysicalBody(target);
            Vector3f start = getBody().getPosition();
            start.add(new Vector3f(0, 10 , 0));
            gun.fireAt(start, enemyBody.getPosition());
        }
    }

    @NotNull
    private Body getBody() {
        return finder.findPhysicalBody(getEntity());
    }

    @NotNull
    private Entity getEntity() {
        return finder.findEntityForComponent(this);
    }

    @NotNull
    private Set<RangedWeapon> getRangedWeapons() {
        return finder.findRangedWeapons(getEntity());
    }
}
