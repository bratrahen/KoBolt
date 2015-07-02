package com.kobot.framework.entitysystem.components;


import com.bulletphysics.dynamics.RigidBody;

public class PhysicsComponent implements Component {
    public RigidBody body;

    public PhysicsComponent(RigidBody body) {
        this.body = body;
    }
}