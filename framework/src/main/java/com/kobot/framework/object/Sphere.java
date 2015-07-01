package com.kobot.framework.object;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.Vector3f;

public class Sphere extends GameObject {
    public Sphere(float massInKilograms, float radiusInMeters, Vector3f position, MotionState motionState) {
        CollisionShape shape = new SphereShape(radiusInMeters);
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
}
