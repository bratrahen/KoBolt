package com.kobot.framework.entitysystem.components.api;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.linearmath.MotionState;
import com.kobot.framework.entitysystem.components.api.basic.UniqueComponent;

public interface RendererComponent extends UniqueComponent {
    MotionState createMotionState();

    CollisionShape getBoundingBox();
}
