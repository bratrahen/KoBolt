package com.kobot.framework.entitysystem.components.api;

import com.kobot.framework.entitysystem.components.api.basic.UniqueComponent;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

/**
 * Created by rahen on 2015-07-06.
 */
public interface Body extends UniqueComponent {
    void applyCentralImpulse(Vector3f force);

    void applyCentralForce(Vector3f force);

    Vector3f getPosition();
}
