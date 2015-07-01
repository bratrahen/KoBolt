package com.test.kobot.object;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public abstract class GameObject {
    public static float STATIC_OBJECT = 0.0f;

    @NotNull
    protected RigidBody body;

    @NotNull
    protected final Vector3f calculateInertia(CollisionShape shape, float mass) {
        Vector3f localInertia = new Vector3f(0, 0, 0);
        boolean isDynamic = mass != 0;
        if (isDynamic) {
            shape.calculateLocalInertia(mass, localInertia);
        }
        return localInertia;
    }

    protected final Transform identityTransform() {
        Transform result = new Transform();
        result.setIdentity();
        return result;
    }

    public Vector3f getPosition() {
        return getWorldTransform().origin;
    }

    public void setPosition(Vector3f position) {
        Transform T = new Transform();
        T.setIdentity();
        T.origin.set(position);
        T.setRotation(getRotationMatrix());
        body.setWorldTransform(T);
    }

    private Quat4f getRotationMatrix() {
        Quat4f R = new Quat4f();
        getWorldTransform().getRotation(R);
        return R;
    }

    private Transform getWorldTransform() {
        Transform T = new Transform();
        body.getWorldTransform(T);
        return T;
    }

    public RigidBody getRigidBody(){
        return body;
    }

    public MotionState getMotionState() {
        return body.getMotionState();
    }
}
