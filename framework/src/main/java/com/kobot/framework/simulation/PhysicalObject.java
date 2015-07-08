package com.kobot.framework.simulation;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import com.kobot.framework.entitysystem.components.api.Body;

import javax.vecmath.Matrix3f;
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
        if (!areEqual(R.m20, -1, eps) && !areEqual(R.m20, 1, eps)){
            eulerY[0] = -Math.asin(R.m20);
            eulerY[1] = Math.PI - eulerY[0];

            eulerX[0] = Math.atan2(R.m21/Math.cos(eulerY[0]), R.m22/Math.cos(eulerY[0]));
            eulerX[1] = Math.atan2(R.m21/Math.cos(eulerY[1]), R.m22/Math.cos(eulerY[1]));

            eulerZ[0] = Math.atan2(R.m10/Math.cos(eulerY[0]), R.m00/Math.cos(eulerY[0]));
            eulerZ[1] = Math.atan2(R.m10/Math.cos(eulerY[1]), R.m00/Math.cos(eulerY[1]));

            if (eulerX[0] > eulerX[1]){
                return new Vector3f((float)eulerX[0], (float)eulerY[0], (float)eulerZ[0]);
            } else {
                return new Vector3f((float)eulerX[1], (float)eulerY[1], (float)eulerZ[1]);
            }


        } else {
            eulerZ[0] = 0;
            if (areEqual(R.m20, -1, eps)){
                eulerY[0] = Math.PI/2.0;
                eulerX[0] = eulerZ[0] + Math.atan2(R.m01, R.m02);
            } else {
                eulerY[0] = -Math.PI/2.0;
                eulerX[0] = -eulerZ[0] + Math.atan2(-R.m01, -R.m02);
            }

            return new Vector3f((float)eulerX[0], (float)eulerY[0], (float)eulerZ[0]);
        }
    }

    private boolean areEqual(double a, double b, double eps){
        return Math.abs(a - b) <= eps;
    }
}


