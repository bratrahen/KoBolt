package com.kobot.common;

public class Ticker {

    private int rate;
    private long s2;

    public static long getTime() {
        return System.currentTimeMillis();
    }

    public Ticker(int tickrateMS) {
        rate = tickrateMS;
        s2 = Ticker.getTime();
    }

    public long getTicks() {
        long i = getTime();
        if (i - s2 > rate) {
            int ticks = (int) ((i - s2) / (long) rate);
            s2 += (long) rate * ticks;
            return ticks;
        }
        return 0;
    }
}
