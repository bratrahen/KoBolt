package com.kobot.framework.entitysystem.components;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.linearmath.MotionState;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.entitysystem.components.factory.CollisionShapeFactory;
import com.kobot.framework.objects.graphic.jpct.JpctMotionState;
import com.threed.jpct.Object3D;

import javax.vecmath.Vector3f;

import static com.kobot.framework.entitysystem.Utilities.*;

public class JpctRendererComponent implements RendererComponent {

    public final Object3D object3D;

    public JpctRendererComponent(Object3D object3D) {
        this.object3D = object3D;

        object3D.rotateY(PI);
        object3D.rotateZ(-PI/2f);
        object3D.rotateMesh();
    }

    public MotionState createMotionState() {
        return new JpctMotionState(object3D);
    }

    public CollisionShape getBoundingBox() {
        float[] floats = object3D.getMesh().getBoundingBox();
        float minX = floats[0];
        float maxX = floats[1];
        float minY = floats[2];
        float maxY = floats[3];
        float minZ = floats[4];
        float maxZ = floats[5];

        Vector3f size = new Vector3f(maxX - minX, maxY - minY, maxZ - minZ);
        return CollisionShapeFactory.createBoxShape(size);
    }
}
