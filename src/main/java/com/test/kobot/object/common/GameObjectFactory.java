package com.test.kobot.object.common;

import com.test.kobot.Scene;
import com.test.kobot.object.Box;
import com.test.kobot.object.Sphere;

import javax.vecmath.Vector3f;
import java.awt.*;

public interface GameObjectFactory {
    Scene createBouncingBallScene();

    Sphere createDynamicSphere(float massInKilograms, float radiusInMeters, Color color, Vector3f position);

    Box createDynamicCube(float massInKilograms, float sideInMeters, Color color, Vector3f position);

    Box createStaticCube( float sideInMeters, Color color, Vector3f position);
}
