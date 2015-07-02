package com.kobot.framework.entitysystem.components.ai;

import com.kobot.framework.entitysystem.components.Component;

public interface AiComponent extends Component {
    void update(float timestepInSeconds);
}
