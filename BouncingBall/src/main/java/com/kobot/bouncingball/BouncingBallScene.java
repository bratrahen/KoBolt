package com.kobot.bouncingball;

import com.kobot.framework.objects.graphic.common.Display;
import com.kobot.framework.Scene;
import com.kobot.framework.objects.physics.Box;
import com.kobot.framework.objects.physics.Sphere;
import com.kobot.framework.objects.graphic.common.GameObjectFactory;

import javax.vecmath.Vector3f;
import java.awt.*;

public class BouncingBallScene extends Scene {

    public BouncingBallScene(GameObjectFactory factory, Display display) {
        super(factory, display);

        final float CUBE_SIDE_IN_M = 100.f;
        final Vector3f CUBE_START_POSITION = new Vector3f(0, -50, 0);
        Box cube = factory.createStaticCube(CUBE_SIDE_IN_M, Color.GREEN, CUBE_START_POSITION);
        addObject(cube);

        final float BALL_MASS_IN_KG = 1.0f;
        final float BALL_RADIUS_IN_M = 10.f;
        final Vector3f BALL_START_POSITION = new Vector3f(0, 100, 0);
        Sphere ball = factory.createDynamicSphere(BALL_MASS_IN_KG, BALL_RADIUS_IN_M, Color.RED, BALL_START_POSITION);
        addObject(ball);
    }
}
