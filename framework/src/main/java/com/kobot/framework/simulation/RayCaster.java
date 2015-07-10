package com.kobot.framework.simulation;

import com.bulletphysics.collision.dispatch.CollisionWorld;

import javax.vecmath.Vector3f;

import static com.kobot.framework.entitysystem.Utilities.scale;

public class RayCaster {
    private final CollisionWorld simulation;
    private final float scale;

    RayCaster(CollisionWorld simulation, float scale) {
        this.scale = scale;
        this.simulation = simulation;
    }

    public CollisionWorld.ClosestRayResultCallback cast(Vector3f from, Vector3f to) {
        Vector3f fromScaled = scale(from, scale);
        Vector3f toScaled = scale(to, scale);
        CollisionWorld.ClosestRayResultCallback result = new CollisionWorld.ClosestRayResultCallback(fromScaled, toScaled);
        simulation.rayTest(fromScaled, toScaled, result);
        return result;
    }
}