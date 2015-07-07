package com.kobot.framework.entitysystem.components;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.api.Body;
import com.kobot.framework.entitysystem.components.api.basic.Component;
import com.kobot.framework.entitysystem.components.factory.PrimitivesFactory;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;

public class RangedWeapon implements Component {
    public final float damage;
    public final float reloadInSeconds;
    private final float forceInNewtons = 1000;
    private final PrimitivesFactory factory;
    private float cooldownInSeconds;

    public RangedWeapon(float damage, float reloadInSeconds, @NotNull PrimitivesFactory factory) {
        this.damage = damage;
        this.reloadInSeconds = reloadInSeconds;
        this.factory = factory;
    }

    public void fireAt(@NotNull Vector3f start, @NotNull Vector3f end) {
        Vector3f direction = new Vector3f(end);
        direction.sub(start);
        direction.normalize();
        fire(start, direction);
    }

    public void fire(@NotNull Vector3f start, @NotNull Vector3f direction) {
        if (!canFire()) {
            return;
        }

        Entity projectile = factory.createCannonBall(start);
        Body body = new ComponentFinder(factory.entityManager).findPhysicalObject(projectile);
        Vector3f forceVector = new Vector3f(direction);
        forceVector.scale(forceInNewtons);
        body.applyCentralImpulse(forceVector);

        cooldownInSeconds = reloadInSeconds;
    }



    public boolean canFire() {
        return cooldownInSeconds <= 0;
    }

    public void reduceCooldown(float timestepInSeconds) {
        cooldownInSeconds -= timestepInSeconds;
        Math.max(0.0, cooldownInSeconds);
    }
}
