package com.kobot.framework.entitysystem.components.api;

import com.kobot.framework.entitysystem.components.api.basic.UniqueComponent;

public interface AiComponent extends UniqueComponent {
    void update(float timestepInSeconds);
}
