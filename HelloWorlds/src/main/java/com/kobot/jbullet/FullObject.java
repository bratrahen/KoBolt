package com.kobot.jbullet;

import com.bulletphysics.dynamics.RigidBody;
import com.threed.jpct.Object3D;

public interface FullObject {
    RigidBody getModel();

    Object3D getView();
}
