package com.kobot.framework.simulation;

import com.bulletphysics.linearmath.DefaultMotionState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import java.util.Arrays;
import java.util.Collection;

import static com.kobot.framework.entitysystem.Utilities.PI;
import static com.kobot.framework.entitysystem.Utilities.rotationMatrixFromEulerZYX;
import static junit.framework.Assert.assertTrue;

@RunWith(Parameterized.class)
public class PhysicalObjectOrientationTest {

    private final float EPSILON = 0.001f;
    private final Vector3f orientationZYX;

    public PhysicalObjectOrientationTest(Vector3f orientationZYX) {
        this.orientationZYX = orientationZYX;
    }

    @Parameterized.Parameters
    public static Collection data() {
        float[] angles = {-2f * PI, -3f * PI / 4f, -PI, -PI / 2f, -PI / 4f, 0, PI / 4f, PI / 2f, PI, 3f * PI / 4f, 2f * PI};

        Vector3f[] orientations = new Vector3f[angles.length * angles.length * angles.length];
        int count = 0;
        for (int iX = 0; iX < angles.length; iX++) {
            for (int iY = 0; iY < angles.length; iY++) {
                for (int iZ = 0; iZ < angles.length; iZ++) {
                    orientations[count++] = new Vector3f(angles[iX], angles[iY], angles[iZ]);
                }
            }
        }

        return Arrays.asList(orientations);
    }

    @Test
    public void testGetRotationMatrix() {
        PhysicalObjectBuilder builder = new StubPhysicalObjectFactory(1.0f).createBuilder();

        builder.setOrientation(orientationZYX);
        PhysicalObject object = builder.build(new DefaultMotionState());

        Matrix3f R_expected = rotationMatrixFromEulerZYX(orientationZYX);
        Matrix3f R_actual = object.getRotationMatrix();

        assertTrue("Rotation matrices not equal for " + orientationZYX, R_expected.epsilonEquals(R_actual, EPSILON)) ;
    }

    @Test
    public void testGetOrientationZYX() {
        PhysicalObjectBuilder builder = new StubPhysicalObjectFactory(1.0f).createBuilder();

        builder.setOrientation(orientationZYX);
        PhysicalObject object = builder.build(new DefaultMotionState());

        Matrix3f R_expected = rotationMatrixFromEulerZYX(orientationZYX);
        Matrix3f R_actual = rotationMatrixFromEulerZYX(object.getOrientation());

        assertTrue("Rotation matrices not equal for " + orientationZYX, R_expected.epsilonEquals(R_actual, EPSILON)) ;
    }
}