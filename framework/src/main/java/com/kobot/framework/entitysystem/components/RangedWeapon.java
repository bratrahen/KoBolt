package com.kobot.framework.entitysystem.components;

import com.kobot.framework.entitysystem.components.api.basic.Component;
import com.kobot.framework.entitysystem.eventbus.events.AttackEvent;
import com.kobot.framework.entitysystem.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;

public class RangedWeapon implements Component {
    public final float damage;
    public final float reloadInSeconds;
    private float cooldownInSeconds;

    public RangedWeapon(float damage, float reloadInSeconds) {
        this.damage = damage;
        this.reloadInSeconds = reloadInSeconds;
    }

    public void fire(@NotNull Vector3f from, @NotNull Vector3f to) {
        EventBus.raiseEvent(new AttackEvent(from, to, damage));
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
