package com.kobot.framework.simulation;

public abstract class PhysicalObjectFactory {
    protected final float scale;

    public PhysicalObjectFactory(float scale) {
        this.scale = scale;
    }

    protected PhysicalObjectBuilder createBuilder(){
        return new PhysicalObjectBuilder(scale);
    }
}
