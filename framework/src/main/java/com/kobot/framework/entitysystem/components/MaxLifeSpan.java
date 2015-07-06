package com.kobot.framework.entitysystem.components;

import com.kobot.framework.entitysystem.components.api.basic.UniqueComponent;

public class MaxLifeSpan implements UniqueComponent {
    private final float maxLifeSpanInSeconds;
    private float lifeSpanInSeconds;


    public MaxLifeSpan(float maxLifeSpanInSeconds) {
        this.maxLifeSpanInSeconds = maxLifeSpanInSeconds;
    }

    public void update(float timeStepInSeconds){
        lifeSpanInSeconds += timeStepInSeconds;
    }

    public boolean shouldDie(){
        return lifeSpanInSeconds > maxLifeSpanInSeconds;
    }
}
