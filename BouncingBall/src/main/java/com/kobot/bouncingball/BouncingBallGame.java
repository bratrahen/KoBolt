package com.kobot.bouncingball;

import com.kobot.framework.Game;
import com.kobot.framework.Scene;
import com.kobot.framework.object.jpct.JpctDisplay;
import com.kobot.framework.object.jpct.JpctGameObjectFactory;

public class BouncingBallGame extends Game {
    Scene scene = new BouncingBallScene(new JpctGameObjectFactory(), new JpctDisplay());

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
