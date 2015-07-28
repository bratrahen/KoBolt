package com.kobot.framework.simulation;

import com.bulletphysics.linearmath.DefaultMotionState;
import com.kobot.framework.entitysystem.Utilities;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import java.util.Arrays;
import java.util.Collection;

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

        Matrix3f R_expected = Utilities.rotationMatrixFromEulerZYX(orientation);
        Matrix3f R_actual = Utilities.rotationMatrixFromEulerZYX(object.getOrientation());

        Utilities.assertMatrixEquals("Rotation matrices not equal", R_expected, R_actual, 0.00001);
    }
}