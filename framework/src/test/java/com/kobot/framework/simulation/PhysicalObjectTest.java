package com.kobot.framework.simulation;

import com.bulletphysics.linearmath.DefaultMotionState;
import org.junit.Test;

import javax.vecmath.Vector3f;

import static org.junit.Assert.*;

/**
 * Created by rahen on 2015-07-08.
 */
public class PhysicalObjectTest {

    @Test
    public void testGetOrientation() throws Exception {
        PhysicalObjectBuilder builder = new PhysicalObjectBuilder();

        Vector3f orientation = new Vector3f(1f, 2f, 3f);
        builder.setOrientation(orientation);
        PhysicalObject object = builder.build(new DefaultMotionState());

        assertEquals(orientation, object.getOrientation());
    }
}