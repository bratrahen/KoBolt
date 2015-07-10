package com.kobot.framework.entitysystem.eventbus;

import java.util.EventListener;

public interface GameEventListener extends EventListener{
    void handle(GameEvent event);
}
