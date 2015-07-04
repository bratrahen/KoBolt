package com.kobot.framework.entitysystem.components.api;

import com.bulletphysics.linearmath.MotionState;

public interface RendererComponent extends UniqueComponent {
    MotionState createMotionState();
}
