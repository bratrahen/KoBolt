package com.kobot.framework.simulation;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import com.kobot.framework.entitysystem.components.api.Body;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class PhysicalObject implements Body {
    private RigidBody rigidBody;

    /**
     * Package scope constructor. Use PhysicalObjectBuilder to create PhysicalObject instance.
     * @param rigidBody
     */
    PhysicalObject(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
    }

    /**
     * Impulse is applied immediately - it is time independent.
     * The result is an equivalent of applying force by 1 second.
     * Point of application is center of gravity.
     * Example:
     * Impulse 10 [N] applied to object of mass of 2 [kg] would immediately increase object's speed by 5 [m/s]
     * @param force measured in Newtons
     */
    public void applyCentralImpulse(Vector3f force){
        rigidBody.applyCentralImpulse(force);
    }

    /**
     * Point of application is center of gravity.
     * @param force measured in Newtons
     */
    public void applyCentralForce(Vector3f force){
        rigidBody.applyCentralForce(force);
    }

    /**
     * Package scope getter. To be used by PhysicsSimulator only.
     * @return
     */
    RigidBody getRigidBody() {
        return rigidBody;
    }

    /**
     * @return [x, y, z] world coordinates measured in meters
     */
    public Vector3f getPosition() {
        return getWorldTransform().origin;
    }

    public Quat4f getRotation() {
        Quat4f R = new Quat4f();
        getWorldTransform().getRotation(R);
        return R;
    }

    private Transform getWorldTransform() {
        Transform T = new Transform();
        rigidBody.getWorldTransform(T);
        return T;
    }
}
