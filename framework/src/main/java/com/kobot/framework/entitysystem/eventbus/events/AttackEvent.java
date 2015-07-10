package com.kobot.framework.entitysystem.eventbus.events;

import com.kobot.framework.entitysystem.eventbus.GameEvent;

import javax.vecmath.Vector3f;

public class AttackEvent implements GameEvent {
    public final Vector3f from;
    public final Vector3f to;
    public final float damage;

    public AttackEvent(Vector3f from, Vector3f to, float damage) {
        this.from = from;
        this.to = to;
        this.damage = damage;
    }
}
