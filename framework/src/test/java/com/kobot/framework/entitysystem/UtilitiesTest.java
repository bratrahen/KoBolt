package com.kobot.framework.entitysystem;

import org.junit.Test;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import static com.kobot.framework.entitysystem.Utilities.*;
import static org.junit.Assert.*;

/**
 * Created by rahen on 2015-08-19.
 */
public class UtilitiesTest {

    @Test
    public void testMul() {
        assertEquals(new Vector3f(14, 32, 50), mul(new Matrix3f(1, 2, 3, 4, 5, 6, 7, 8, 9), new Vector3f(1, 2, 3)));
        assertEquals(new Vector3f(1, 2, 3), mul(identityMatrix(), new Vector3f(1, 2, 3)));
    }
}