package com.kobot.framework.simulation;

import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MatrixUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PhysicalObjectTest {

    private final Vector3f orientation;

    public PhysicalObjectTest(Vector3f orientation) {
        this.orientation = orientation;
    }

    @Parameterized.Parameters
    public static Collection data() {
        final float PI = (float)Math.PI;

        return Arrays.asList(new Vector3f(1f, 2f, 3f),
                new Vector3f(1f, PI/2f, 3),
                new Vector3f(1f, -PI/2f, 3));
    }

    @Test
    public void testGetOrientation() throws Exception {
        PhysicalObjectBuilder builder = new StubPhysicalObjectFactory(1.0f).createBuilder();

        builder.setOrientation(orientation);
        PhysicalObject object = builder.build(new DefaultMotionState());

        Matrix3f R_expected = new Matrix3f();
        MatrixUtil.setEulerZYX(R_expected, orientation.x, orientation.y, orientation.z);

        Vector3f actual = object.getOrientation();
        Matrix3f R_actual = new Matrix3f();
        MatrixUtil.setEulerZYX(R_actual, actual.x, actual.y, actual.z);

        assertMatrixEquals("Rotation matrices not equal", R_expected, R_actual, 0.00001);
    }

    private void assertMatrixEquals(String msg, Matrix3f expected, Matrix3f actual, double delta){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                assertEquals(msg, expected.getElement(i,j), actual.getElement(i,j), delta);
            }
        }
    }
}