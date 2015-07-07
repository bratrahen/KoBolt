package com.kobot.framework.simulation;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

public class PhysicalObjectBuilder {
    private CollisionShape shape;
    private float angularDamping;
    private float restitution;
    private float linearDamping;
    private float mass;
    private Vector3f position = new Vector3f();
    private Vector3f orientation = new Vector3f();

    /**
     * @param mass measured in kilograms [kg]
     */
    public PhysicalObjectBuilder setMass(float mass) {
        this.mass = mass;
        return this;
    }

    /**
     * @param position world coordinated [x, y, z] measured in meters [m]
     * @return
     */
    public PhysicalObjectBuilder setPosition(Vector3f position) {
        this.position = new Vector3f(position);
        return this;
    }


    /**
     * @param orientation counterclockwise rotation [rotX, rotY, rotZ] around world axises measured in radians [rad]
     * @return
     */
    public PhysicalObjectBuilder setOrientation(Vector3f orientation) {
        this.orientation = new Vector3f(orientation);
        return this;
    }

    public PhysicalObjectBuilder setShape(CollisionShape shape) {
        this.shape = shape;
        return this;
    }

    public PhysicalObjectBuilder setRestitution(float restitution) {
        this.restitution = restitution;
        return this;
    }

    public PhysicalObjectBuilder setLinearDamping(float linearDamping) {
        this.linearDamping = linearDamping;
        return this;
    }

    public PhysicalObjectBuilder setAngularDamping(float angularDamping) {
        this.angularDamping = angularDamping;
        return this;
    }

    public PhysicalObject build(MotionState motionState) {
        motionState.setWorldTransform(calculateWorldTransform());

        RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(mass, motionState, shape, calculateInertia());
        rbInfo.restitution = restitution;
        rbInfo.linearDamping = linearDamping;
        rbInfo.angularDamping = angularDamping;

        RigidBody rigidBody = new RigidBody(rbInfo);
        return new PhysicalObject(rigidBody);
    }

    protected final Vector3f calculateInertia() {
        Vector3f localInertia = new Vector3f(0, 0, 0);
        boolean isDynamic = mass != 0;
        if (isDynamic) {
            shape.calculateLocalInertia(mass, localInertia);
        }
        return localInertia;
    }

    protected final Transform calculateWorldTransform(){
        Transform transform = new Transform();
        transform.setIdentity();
        transform.origin.set(position);

        Matrix3f Rx = new Matrix3f();
        Rx.setIdentity();
        Rx.rotX(orientation.x);

        Matrix3f Ry = new Matrix3f();
        Ry.setIdentity();
        Ry.rotY(orientation.y);

        Matrix3f Rz = new Matrix3f();
        Rz.setIdentity();
        Rz.rotZ(orientation.z);

        Matrix3f Rxyz = new Matrix3f();
        Rxyz.setIdentity();
        Rxyz.mul(Rx, Ry);
        Rxyz.mul(Rxyz, Rz);
        transform.basis.set(Rxyz);

        return transform;
    }
}
