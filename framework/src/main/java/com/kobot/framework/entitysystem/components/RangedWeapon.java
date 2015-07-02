package com.kobot.framework.entitysystem.components;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.factory.EntityFactory;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;
import java.awt.*;

public class RangedWeapon implements Component {
    public final float damage;
    public final float reloadInSeconds;
    private final float forceInNewtons = 100;
    private final EntityFactory factory;
    private float cooldownInSeconds;

    public RangedWeapon(float damage, float reloadInSeconds, @NotNull EntityFactory factory) {
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

        Entity projectile = factory.createDynamicSphere(10, 1, Color.ORANGE, start);
        PhysicsComponent physics = (PhysicsComponent) factory.entityManager.getComponentForEntity(PhysicsComponent.class, projectile);
        Vector3f forceVector = new Vector3f(direction);
        forceVector.scale(forceInNewtons);
        physics.body.applyCentralImpulse(forceVector);

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
