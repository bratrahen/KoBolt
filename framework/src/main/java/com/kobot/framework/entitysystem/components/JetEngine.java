package com.kobot.framework.entitysystem.components;

import com.kobot.framework.entitysystem.components.api.basic.UniqueComponent;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityFinder;

import javax.vecmath.Vector3f;

public class JetEngine implements UniqueComponent {
    private final float maxThrust;
    private float percentage;
    private Vector3f direction = new Vector3f();

    public JetEngine(float maxThrust) {
        this.maxThrust = Math.abs(maxThrust);
        this.percentage = 1f;
    }

    public void setThrustPercentage(float percentage) {
        this.percentage = Math.max(0, percentage);
        this.percentage = Math.min(percentage, 1);
    }

    public void setThrustDirection(Vector3f direction) {
        this.direction = new Vector3f(direction);
        this.direction.normalize();
    }

    public Vector3f getThrust(){
        Vector3f thrustForce = new Vector3f(direction);
        thrustForce.scale(-maxThrust * percentage);
        return thrustForce;
    }
}
