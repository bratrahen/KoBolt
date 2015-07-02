package com.kobot.framework;

public class Utilities {
    private static Clock clock = new Clock();

    // For UnitTests
    public static void setClock(Clock clock) {
        Utilities.clock = clock;
    }

    public static long currentTimeInMilliSeconds() {
        return clock.currentTimeInMilliSeconds();
    }

    public static float currentTimeInSeconds() {
        return clock.currentTimeInSeconds();
    }
}
