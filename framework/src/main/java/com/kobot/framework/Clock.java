package com.kobot.framework;

public class Clock {

    public long currentTimeInMilliSeconds(){
        return System.currentTimeMillis();
    }

    public float currentTimeInSeconds(){
        return currentTimeInMilliSeconds()/1000f;
    }
}
