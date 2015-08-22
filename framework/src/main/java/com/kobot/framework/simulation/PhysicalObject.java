package com.kobot.framework.simulation;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import com.kobot.framework.entitysystem.components.api.Body;

import javax.vecmath.Matrix3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import static com.kobot.framework.entitysystem.Utilities.areEqual;
import static com.kobot.framework.entitysystem.Utilities.limitFromMinusPiToPlusPi;

public class PhysicalObject implements Body {
    private final RigidBody rigidBody;
    private final float scale;

    /**
     * Package scope constructor. Use PhysicalObjectBuilder to create PhysicalObject instance.
     *
     * @param rigidBody
     */
    PhysicalObject(RigidBody rigidBody, float scale) {
        this.rigidBody = rigidBody;
        this.scale = scale;

        this.rigidBody.setUserPointer(this);
    }

    /**
     * Impulse is applied immediately - it is time independent.
     * The result is an equivalent of applying force by 1 second.
     * Point of application is center of gravity.
     * Example:
     * Impulse 10 [N] applied to object of mass of 2 [kg] would immediately increase object's speed by 5 [m/s]
     * @param impulse measured in Newtons [N]
     */
    public void applyCentralImpulse(Vector3f impulse) {
        Vector3f scaledImpulse = new Vector3f(impulse);
        scaledImpulse.scale(scale);
        rigidBody.applyCentralImpulse(scaledImpulse);
    }

    /**
     * Point of application is center of gravity.
     * @param force measured in Newtons [N]
     */
    public void applyCentralForce(Vector3f force) {
        Vector3f scaledForce = new Vector3f(force);
        scaledForce.scale(scale);
        rigidBody.applyCentralForce(force);
    }

//    /**
//     * Impulse is applied immediately - it is time independent.
//     * The result is an equivalent of applying torque by 1 second.
//     * @param torque measured in Newton Metre [N?m]
//     */
//    public void applyTorqueImpulse(Vector3f torque) {
//       scale(sclae*scale)
//       rigidBody.applyTorqueImpulse(torque);
//    }

    public Vector3f getLinearVelocity() {
        Vector3f result = new Vector3f();
        rigidBody.getLinearVelocity(result);
        result.scale(1f / scale);
        return result;
    }

    public Vector3f getAngularVelocity() {
        Vector3f result = new Vector3f();
        rigidBody.getAngularVelocity(result);
        return result;
    }

    public void setAngularVelocity(Vector3f angularVelocity) {
        rigidBody.setAngularVelocity(angularVelocity);
    }

    public boolean isInWorld() {
        return rigidBody.isInWorld();
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
        Vector3f position = getWorldTransform().origin;
        position.scale(1f / scale);
        return position;
    }

    private Transform getWorldTransform() {
        Transform T = new Transform();
        rigidBody.getWorldTransform(T);
        return T;
    }

    /**
     * Object's orientation in world space as Euler Angles
     * @return [eulerX, eulerY, eulerZ]
     */
    public Vector3f getOrientation() {
        //http://staff.city.ac.uk/~sbbh653/publications/euler.pdf
        Matrix3f R = getWorldTransform().basis;

        double[] eulerX = new double[2];
        double[] eulerY = new double[2];
        double[] eulerZ = new double[2];

        final double eps = 0.000001;
        if (!areEqual(R.m20, -1, eps) && !areEqual(R.m20, 1, eps)) {
            eulerY[0] = -Math.asin(R.m20);
            eulerY[1] = Math.PI - eulerY[0];

            eulerX[0] = Math.atan2(R.m21 / Math.cos(eulerY[0]), R.m22 / Math.cos(eulerY[0]));
            eulerX[1] = Math.atan2(R.m21 / Math.cos(eulerY[1]), R.m22 / Math.cos(eulerY[1]));

            eulerZ[0] = Math.atan2(R.m10 / Math.cos(eulerY[0]), R.m00 / Math.cos(eulerY[0]));
            eulerZ[1] = Math.atan2(R.m10 / Math.cos(eulerY[1]), R.m00 / Math.cos(eulerY[1]));

            if (eulerX[0] > eulerX[1]) {
                return limitFromMinusPiToPlusPi((float) eulerX[0], (float) eulerY[0], (float) eulerZ[0]);
            } else {
                return limitFromMinusPiToPlusPi((float) eulerX[1], (float) eulerY[1], (float) eulerZ[1]);
            }
        } else {
            eulerZ[0] = 0;
            if (areEqual(R.m20, -1, eps)) {
                eulerY[0] = Math.PI / 2.0;
                eulerX[0] = eulerZ[0] + Math.atan2(R.m01, R.m02);
            } else {
                eulerY[0] = -Math.PI / 2.0;
                eulerX[0] = -eulerZ[0] + Math.atan2(-R.m01, -R.m02);
            }

            return limitFromMinusPiToPlusPi((float) eulerX[0], (float) eulerY[0], (float) eulerZ[0]);
        }
    }

    private Quat4f getQuaternion() {
        Quat4f quat4f = new Quat4f();
        getWorldTransform().getRotation(quat4f);
        return quat4f;
    }


    public Matrix3f getRotationMatrix() {
//        Matrix3f R = new Matrix3f();
//        MatrixUtil.getRotation(R, getQuaternion());
//        return R;
        return getWorldTransform().basis;
    }
}


