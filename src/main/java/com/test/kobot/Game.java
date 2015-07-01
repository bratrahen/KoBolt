package com.test.kobot;

public abstract class Game {
    public final static long TIMESTEP_IN_MILLIS = 1000/60;
    public final static float TIMESTEP_IN_SEC = 1/60f;

    public final void run() {
        long currentTimeInMillis = getTimeMillis();
        long accumulatorInMillis = 0;

        while (!shouldQuit()) {
            long newTimeInMillis = getTimeMillis();
            long frameTimeInMillis = newTimeInMillis - currentTimeInMillis;
            currentTimeInMillis = newTimeInMillis;

            accumulatorInMillis += frameTimeInMillis;

            while (accumulatorInMillis >= TIMESTEP_IN_MILLIS) {
                simulate(TIMESTEP_IN_SEC);
                accumulatorInMillis -= TIMESTEP_IN_MILLIS;
            }

            render();
        }
    }

    protected long getTimeMillis() {
        return System.currentTimeMillis();
    }

    protected abstract boolean shouldQuit();

    protected abstract void simulate(float timestepInSeconds);

    protected abstract void render();
}
