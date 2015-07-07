package com.kobot.framework.entitysystem.components.body;


import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import com.kobot.framework.entitysystem.components.api.Body;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class PrimitiveBody implements Body {
    private RigidBody body;

    public PrimitiveBody(RigidBody body) {
        this.body = body;
    }

    public void applyCentralImpulse(Vector3f force){
        body.applyCentralImpulse(force);
    }

    public void applyCentralForce(Vector3f force){
        body.applyCentralForce(force);
    }

    public Vector3f getPosition() {
        return getWorldTransform().origin;
    }

    public void setPosition(Vector3f position) {
        Transform T = new Transform();
        T.setIdentity();
        T.origin.set(position);
        T.setRotation(getRotation());
        body.setWorldTransform(T);
    }

    public Quat4f getRotation() {
        Quat4f R = new Quat4f();
        getWorldTransform().getRotation(R);
        return R;
    }

    private Transform getWorldTransform() {
        Transform T = new Transform();
        body.getWorldTransform(T);
        return T;
    }

    public void addToSimulation(DynamicsWorld simulation) {
        simulation.addRigidBody(body);
    }

    public void removeFromSimulation(DynamicsWorld simulation) {
        simulation.removeRigidBody(body);
    }
}