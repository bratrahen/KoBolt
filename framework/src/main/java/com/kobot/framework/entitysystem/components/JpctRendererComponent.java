package com.kobot.framework.entitysystem.components;

import com.bulletphysics.linearmath.MotionState;
import com.kobot.framework.objects.graphic.jpct.JpctMotionState;
import com.threed.jpct.Object3D;

public class JpctRendererComponent implements RendererComponent {

    public final Object3D object3D;

    public JpctRendererComponent(Object3D object3D) {
        this.object3D = object3D;
    }

    public MotionState createMotionState() {
        return new JpctMotionState(object3D);
    }
}
