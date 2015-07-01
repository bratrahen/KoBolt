package com.kobot.framework.object;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.Vector3f;

public class Box extends GameObject {
    private Box(float massInKilograms, float[] halfExtends, Vector3f position, MotionState motionState) {
        CollisionShape shape = new BoxShape(new Vector3f(halfExtends));
        Vector3f localInertia = calculateInertia(shape, massInKilograms);

        Transform transform = new Transform();
        transform.setIdentity();
        transform.origin.set(position);
        motionState.setWorldTransform(transform);

        RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(massInKilograms, motionState, shape, localInertia);
        rbInfo.restitution = 1.0f;
        rbInfo.linearDamping = 0.0f;
        rbInfo.angularDamping = 0.0f;
        body = new RigidBody(rbInfo);
    }

    public Box(float massInKilograms, float sideInMeters, Vector3f position, MotionState motionState) {
        this(massInKilograms, new float[]{sideInMeters/2f, sideInMeters/2f, sideInMeters/2f}, position, motionState);
    }
}
