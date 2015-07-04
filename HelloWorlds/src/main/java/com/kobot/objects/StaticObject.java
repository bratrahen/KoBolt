package com.kobot.objects;

import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

import java.io.Serializable;

public class StaticObject implements Serializable {
    private Object3D model;

    public void addToLocation(World location) {
        location.addObject(getModel());
    }

    public SimpleVector getPosition() {
        return model.getTransformedCenter();
    }

    public Object3D getModel() {
        return model;
    }

    protected void setModel(Object3D model) {
        this.model = model;
    }
}
