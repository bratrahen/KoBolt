package com.kobot.framework.objects.graphic.common;

import com.kobot.framework.objects.physics.Box;
import com.kobot.framework.objects.physics.Sphere;

import javax.vecmath.Vector3f;
import java.awt.*;

public interface GameObjectFactory {
    Sphere createDynamicSphere(float massInKilograms, float radiusInMeters, Color color, Vector3f position);

    Box createDynamicCube(float massInKilograms, float sideInMeters, Color color, Vector3f position);

    Box createStaticCube( float sideInMeters, Color color, Vector3f position);
}
