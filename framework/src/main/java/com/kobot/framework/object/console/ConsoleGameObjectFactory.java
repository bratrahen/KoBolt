package com.kobot.framework.object.console;

import com.bulletphysics.linearmath.DefaultMotionState;
import com.kobot.framework.object.Box;
import com.kobot.framework.object.GameObject;
import com.kobot.framework.object.Sphere;
import com.kobot.framework.object.common.GameObjectFactory;

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
