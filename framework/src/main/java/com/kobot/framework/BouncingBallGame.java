package com.kobot.framework;

import com.kobot.framework.object.jpct.JpctGameObjectFactory;
import com.kobot.framework.object.common.GameObjectFactory;

public class BouncingBallGame extends Game {
    private final GameObjectFactory factory = new JpctGameObjectFactory();
    Scene scene = factory.createBouncingBallScene();

    public static void main(String[] args) {
        new BouncingBallGame().run();
    }

    @Override
    protected boolean shouldQuit() {
        return false;
    }

    @Override
    protected void simulate(float timestepInSeconds) {
        scene.stepSimulation(timestepInSeconds);
    }

    @Override
    protected void render() {
        scene.render();
    }
}
