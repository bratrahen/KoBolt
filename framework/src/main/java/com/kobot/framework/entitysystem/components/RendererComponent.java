package com.kobot.framework.entitysystem.components;

import com.bulletphysics.linearmath.MotionState;

public interface RendererComponent extends Component {
    MotionState createMotionState();
}
