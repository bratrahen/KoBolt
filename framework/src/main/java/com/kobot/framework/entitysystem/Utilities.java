package com.kobot.framework.entitysystem;

import com.bulletphysics.linearmath.MatrixUtil;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import static org.junit.Assert.assertEquals;

public class Utilities {
    public static final float PI = (float) Math.PI;

    public static Vector3f scale(Vector3f vector, float scale) {
        Vector3f result = new Vector3f(vector);
        result.scale(scale);
        return result;
    }

    public static void assertMatrixEquals(String msg, Matrix3f expected, Matrix3f actual, double delta){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                assertEquals(msg, expected.getElement(i,j), actual.getElement(i,j), delta);
            }
        }
    }

    @NotNull
    public static Matrix3f rotationMatrixFromEulerZYX(Vector3f eulerZYX) {
        Matrix3f R_actual = new Matrix3f();
        MatrixUtil.setEulerZYX(R_actual, eulerZYX.x, eulerZYX.y, eulerZYX.z);
        return R_actual;
    }
}
