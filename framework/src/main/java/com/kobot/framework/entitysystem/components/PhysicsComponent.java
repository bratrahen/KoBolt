package com.kobot.framework.entitysystem.components;


import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class PhysicsComponent implements Component {
    public RigidBody body;

    public PhysicsComponent(RigidBody body) {
        this.body = body;
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

}