package com.kobot.framework.entitysystem.components.factory;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;

import javax.vecmath.Vector3f;

public class CollisionShapeFactory {
    private CollisionShapeFactory() {
    }

    public static CollisionShape createBoxShape(Vector3f size) {
        Vector3f halfExtends = new Vector3f();
        halfExtends.scale(0.5f, size);
        return new BoxShape(halfExtends);
    }
}
