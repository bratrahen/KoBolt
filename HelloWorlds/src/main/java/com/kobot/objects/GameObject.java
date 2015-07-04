package com.kobot.objects;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;

import javax.vecmath.Vector3f;

public class GameObject extends RigidBody {
    public GameObject(RigidBodyConstructionInfo constructionInfo) {
        super(constructionInfo);
    }

    public GameObject(float mass, MotionState motionState, CollisionShape collisionShape) {
        super(mass, motionState, collisionShape);
    }

    public GameObject(float mass, MotionState motionState, CollisionShape collisionShape, Vector3f localInertia) {
        super(mass, motionState, collisionShape, localInertia);
    }
}
