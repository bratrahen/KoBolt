package com.test.kobot.object.console;

import com.bulletphysics.linearmath.DefaultMotionState;
import com.test.kobot.BouncingBallScene;
import com.test.kobot.Scene;
import com.test.kobot.object.Box;
import com.test.kobot.object.GameObject;
import com.test.kobot.object.Sphere;
import com.test.kobot.object.common.GameObjectFactory;

import javax.vecmath.Vector3f;
import java.awt.*;

public class ConsoleGameObjectFactory implements GameObjectFactory {

    public Scene createBouncingBallScene() {
        return new BouncingBallScene(this, new ConsoleDisplay());
    }

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
