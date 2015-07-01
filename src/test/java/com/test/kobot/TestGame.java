package com.test.kobot;

import com.bulletphysics.dynamics.DynamicsWorld;

public class TestGame extends Game {

    public int maxGameLoop = 10;
    public long tickInMillis = 1;

    public int gameLoopCounter = 0;
    public int renderCounter = 0;
    public int simulationCounter = 0;

    public float simulationTimeInSeconds = 0f;
    public long timeInMillis = 0;

    public DynamicsWorld simulation = null;

    @Override
    protected boolean shouldQuit() {
        if (maxGameLoop <= gameLoopCounter){
            return true;
        } else {
            gameLoopCounter++;
            return false;
        }
    }

    @Override
    protected void simulate(float timestepInSeconds) {
        simulationCounter++;
        simulationTimeInSeconds += timestepInSeconds;

        if (simulation != null){
            simulation.stepSimulation(timestepInSeconds, 0);
        }
    }

    @Override
    protected void render() {
        renderCounter++;
    }

    protected long getTimeMillis() {
        timeInMillis += tickInMillis;
        return timeInMillis;
    }
}
