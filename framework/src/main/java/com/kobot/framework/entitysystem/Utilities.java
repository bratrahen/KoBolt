package com.kobot.framework.entitysystem;

import com.bulletphysics.linearmath.MatrixUtil;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Matrix3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import static org.junit.Assert.assertEquals;

public class Utilities {
    public static final float PI = (float) Math.PI;

    public static Matrix3f identityMatrix(){
        Matrix3f result = new Matrix3f();
        result.setIdentity();
        return result;
    }

    public static Vector3f scale(Vector3f vector, float scale) {
        Vector3f result = new Vector3f(vector);
        result.scale(scale);
        return result;
    }

    public static Vector3f limitFromMinusPiToPlusPi(Vector3f eulerAngles){
        return limitFromMinusPiToPlusPi(eulerAngles.x , eulerAngles.y, eulerAngles.z);
    }

    public static Vector3f limitFromMinusPiToPlusPi(float eulerX, float eulerY, float eulerZ){
        float limitedX = limitFromMinusPiToPlusPi(eulerX);
        float limitedY = limitFromMinusPiToPlusPi(eulerY);
        float limitedZ = limitFromMinusPiToPlusPi(eulerZ);

        return new Vector3f(limitedX, limitedY, limitedZ);
    }

    public static float limitFromMinusPiToPlusPi(float euler){
        float limitedFromMinus2PiToPlus2Pi = euler % (2f*PI);

        if (limitedFromMinus2PiToPlus2Pi > PI){
            return -2f*PI + limitedFromMinus2PiToPlus2Pi;
        } else if (limitedFromMinus2PiToPlus2Pi < -PI){
            return 2f*PI - limitedFromMinus2PiToPlus2Pi;
        } else {
            return limitedFromMinus2PiToPlus2Pi;
        }

//
//        float limitedFromZeroToPlus4Pi = limitedFromMinus2PiToPlus2Pi + 2f*PI;
//        float limitedFromMinusPiToPlusPi =
//
//        if (limitedEuler <= PI){
//            return limitedEuler;
//        } else {
//            return 2f * PI - limitedEuler;
//        }
    }

    public static void assertMatrixEquals(Matrix3f expected, Matrix3f actual, double delta){
        assertMatrixEquals("Matrices not equal", expected, actual, delta);
    }

    public static void assertMatrixEquals(String message, Matrix3f expected, Matrix3f actual, double delta){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                String messageWithIndexes = message + ": [" + i + "][" + j +"] = ";
                assertEquals(messageWithIndexes, expected.getElement(i,j), actual.getElement(i,j), delta);
            }
        }
    }

    @NotNull
    public static Matrix3f rotationMatrixFromEulerZYX(Vector3f eulerZYX) {
        Matrix3f R_actual = new Matrix3f();
        MatrixUtil.setEulerZYX(R_actual, eulerZYX.x, eulerZYX.y, eulerZYX.z);
        return R_actual;
    }

    /**
     * x, y and z definitions:
     *     +X to the right
     *     +Y straight up
     *     +Z axis toward viewer
     *
     * @param eulerX Bank applied last (rotation about local x axis - which is front-back)
     * @param eulerY Heading applied first (rotation about local y axis – which is up-down)
     * @param eulerZ Attitude applied second (rotation about local z axis - which is along wing)
     * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/">quaternionToEuler</a>
     * @see <a href="http://www.euclideanspace.com/maths/standards/index.htm">standards</a>
     */
    public static Quat4f quaternionFromEulerYZX(Vector3f eulerAngles) {
        return quaternionFromEulerYZX(eulerAngles.x, eulerAngles.y, eulerAngles.z);
    }
    /**
     * x, y and z definitions:
     *     +X to the right
     *     +Y straight up
     *     +Z axis toward viewer
     *
     * @param eulerX Bank applied last (rotation about local x axis - which is front-back)
     * @param eulerY Heading applied first (rotation about local y axis – which is up-down)
     * @param eulerZ Attitude applied second (rotation about local z axis - which is along wing)
     * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/">quaternionToEuler</a>
     * @see <a href="http://www.euclideanspace.com/maths/standards/index.htm">standards</a>
     */
    public static Quat4f quaternionFromEulerYZX(float eulerX, float eulerY, float eulerZ) {
        double heading = eulerY;
        double attitude = eulerZ;
        double bank = eulerX;
        // Assuming the angles are in radians.
        double c1 = Math.cos(heading/2.0);
        double s1 = Math.sin(heading / 2.0);
        double c2 = Math.cos(attitude / 2.0);
        double s2 = Math.sin(attitude / 2.0);
        double c3 = Math.cos(bank / 2.0);
        double s3 = Math.sin(bank / 2.0);
        double c1c2 = c1*c2;
        double s1s2 = s1*s2;

        double w = c1c2 * c3 - s1s2 * s3;
        double x = c1c2 * s3 + s1s2 * c3;
        double y = s1 * c2 * c3 + c1 * s2 * s3;
        double z = c1 * s2 * c3 - s1 * c2 * s3;

        Quat4f quat4f = new Quat4f();
        quat4f.x = (float)x;
        quat4f.y = (float)y;
        quat4f.z = (float)z;
        quat4f.w = (float)w;

        return quat4f;
    }

    /**
     * Convert quaternion to Euler Angles (NASA standard Aeroplane)
     * x, y and z definitions:
     *     +X to the right
     *     +Y straight up
     *     +Z axis toward viewer
     * Heading applied first (rotation about local y axis – which is up-down)
     * Attitude applied second (rotation about local z axis - which is along wing)
     * Bank applied last (rotation about local x axis - which is front-back)
     * @param q1 quaternion to convert
     * @return [Bank, Heading, Attitude]
     * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/">Quaternion To Euler Conversion</a>
     * @see <a href="http://www.euclideanspace.com/maths/standards/index.htm">Standards</a>
     */
    public static Vector3f quaternionToEuler(Quat4f q1) {
        double heading; //rotation about y axis
        double attitude; //rotation about z axis
        double bank; //rotation about x axis

        double test = q1.x*q1.y + q1.z*q1.w;
        if (test > 0.499) { // singularity at north pole
            heading = 2f * Math.atan2(q1.x, q1.w);
            attitude = Math.PI/2.0;
            bank = 0.0;
            return new Vector3f((float)bank, (float)heading, (float)attitude);
        }
        if (test < -0.499) { // singularity at south pole
            heading = -2.0 * Math.atan2(q1.x, q1.w);
            attitude = - Math.PI/2.0;
            bank = 0.0;
            return new Vector3f((float)bank, (float)heading, (float)attitude);
        }
        double sqx = q1.x*q1.x;
        double sqy = q1.y*q1.y;
        double sqz = q1.z*q1.z;
        heading = Math.atan2(2 * q1.y * q1.w - 2 * q1.x * q1.z, 1 - 2 * sqy - 2 * sqz);
        attitude = Math.asin(2 * test);
        bank = Math.atan2(2 * q1.x * q1.w - 2 * q1.y * q1.z, 1 - 2 * sqx - 2 * sqz);

        return new Vector3f((float)bank, (float)heading, (float)attitude);
    }

    public static Vector3f mul(Matrix3f m, Vector3f v){
        Vector3f result = new Vector3f();
        result.x = m.m00*v.x + m.m01*v.y + m.m02*v.z;
        result.y = m.m10*v.x + m.m11*v.y + m.m12*v.z;
        result.z = m.m20*v.x + m.m21*v.y + m.m22*v.z;
        return result;
    }

    public static boolean areEqual(double a, double b, double eps){
        return Math.abs(a - b) <= eps;
    }
}
