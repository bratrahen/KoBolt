package com.kobot.framework.entitysystem;

import javax.vecmath.Vector3f;

public class Utilities {
    public static final float PI = (float) Math.PI;

    public static Vector3f scale(Vector3f vector, float scale) {
        Vector3f result = new Vector3f(vector);
        result.scale(scale);
        return result;
    }
}
