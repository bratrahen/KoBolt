package com.kobot.framework.objects.graphic.console;

import com.bulletphysics.linearmath.DefaultMotionState;
import com.kobot.framework.objects.physics.Box;
import com.kobot.framework.objects.physics.GameObject;
import com.kobot.framework.objects.physics.Sphere;
import com.kobot.framework.objects.graphic.common.GameObjectFactory;

import javax.vecmath.Vector3f;
import java.awt.*;

public class ConsoleGameObjectFactory implements GameObjectFactory {

    public Sphere createDynamicSphere(float massInKilograms, float radiusInMeters, Color color, Vector3f position) {
        return new Sphere(massInKilograms, radiusInMeters, position, new DefaultMotionState());
    }

    public Box createStaticCube(float sideInMeters, Color color, Vector3f position) {
        return createDynamicCube(GameObject.STATIC_OBJECT, sideInMeters, color, position);
    }

    public Box createDynamicCube(float massInKilograms, float sideInMeters, Color color, Vector3f position) {
        return new Box(massInKilograms, sideInMeters, position, new DefaultMotionState());
    }
}
