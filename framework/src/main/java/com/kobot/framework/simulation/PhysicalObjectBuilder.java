package com.kobot.framework.simulation;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.Vector3f;

import static com.kobot.framework.entitysystem.Utilities.rotationMatrixFromEulerZYX;

public class PhysicalObjectBuilder {
    private final float scale;

    private CollisionShape shape;
    private float angularDamping;
    private float restitution;
    private float linearDamping;
    private float mass;
    private Vector3f position = new Vector3f();
    private Vector3f orientation = new Vector3f();

    PhysicalObjectBuilder(float scale) {
        this.scale = scale;
    }

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
        this.position.scale(scale);
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
        return new PhysicalObject(rigidBody, scale);
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
        transform.basis.set(rotationMatrixFromEulerZYX(orientation));
        return transform;
    }
}
