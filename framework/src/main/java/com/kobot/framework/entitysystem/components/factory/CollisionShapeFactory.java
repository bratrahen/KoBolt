package com.kobot.framework.entitysystem.components.factory;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;

import javax.vecmath.Vector3f;

public class CollisionShapeFactory {
    public static CollisionShape createBoxShape(Vector3f size) {
        Vector3f halfExtends = new Vector3f();
        halfExtends.scale(0.5f, size);
        return new BoxShape(halfExtends);
    }

    public static CollisionShape createSphereShape(float radius) {
        return new SphereShape(radius);
    }
}
