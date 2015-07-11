package com.kobot.framework.entitysystem.eventbus.events;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.eventbus.GameEvent;

public class CreateEntityEvent implements GameEvent{
    public final Entity entity;

    public CreateEntityEvent(Entity entity) {
        this.entity = entity;
    }
}
