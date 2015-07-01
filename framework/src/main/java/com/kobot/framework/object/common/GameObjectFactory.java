package com.kobot.framework.object.common;

import com.kobot.framework.object.Box;
import com.kobot.framework.object.Sphere;
import com.kobot.framework.Scene;

import javax.vecmath.Vector3f;
import java.awt.*;

public interface GameObjectFactory {
    Scene createBouncingBallScene();

    Sphere createDynamicSphere(float massInKilograms, float radiusInMeters, Color color, Vector3f position);

    Box createDynamicCube(float massInKilograms, float sideInMeters, Color color, Vector3f position);

    Box createStaticCube( float sideInMeters, Color color, Vector3f position);
}
