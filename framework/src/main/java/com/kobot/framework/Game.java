package com.kobot.framework;

import com.kobot.framework.controls.Controls;

public abstract class Game {
    public final static long TIMESTEP_IN_MILLIS = 1000/60;
    public final static float TIMESTEP_IN_SEC = 1/60f;
    private long currentTimeInMillis;
    private long accumulatorInMillis;

    public Controls getControls() {
        return controls;
    }

    private final Controls controls = new Controls();

    public final void run() {
        currentTimeInMillis = getTimeMillis();
        accumulatorInMillis = 0;

        while (!shouldQuit()) {
            processInput();
            updateGame();
            render();
        }
    }

    private void processInput() {
        controls.processInput();
    }

    private void updateGame() {
        long newTimeInMillis = getTimeMillis();
        long frameTimeInMillis = newTimeInMillis - currentTimeInMillis;
        currentTimeInMillis = newTimeInMillis;

        accumulatorInMillis += frameTimeInMillis;

        while (accumulatorInMillis >= TIMESTEP_IN_MILLIS) {
            simulate(TIMESTEP_IN_SEC);
            accumulatorInMillis -= TIMESTEP_IN_MILLIS;
        }
    }

    protected long getTimeMillis() {
        return System.currentTimeMillis();
    }

    protected abstract boolean shouldQuit();

    protected abstract void simulate(float timestepInSeconds);

    protected abstract void render();
}
