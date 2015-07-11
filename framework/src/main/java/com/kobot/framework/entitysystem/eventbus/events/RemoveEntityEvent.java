package com.kobot.framework.entitysystem.eventbus.events;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.eventbus.GameEvent;

public class RemoveEntityEvent implements GameEvent {
    public final Entity entity;

    public RemoveEntityEvent(Entity entity) {
        this.entity = entity;
    }
}
