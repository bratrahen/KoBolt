package com.kobot.objects;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.helloworld.JpctMotionState;
import com.kobot.jbullet.FullObject;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;

import javax.vecmath.Vector3f;

public class Cuboid implements FullObject {
    private final RigidBody model;
    private final Object3D view;

    public Cuboid(float mass, float a) {

        CollisionShape colShape = new BoxShape(new Vector3f(a, a, a));
        Vector3f localInertia = new Vector3f(0, 0, 0);

        boolean isDynamic = mass != 0;
        if (isDynamic) {
            colShape.calculateLocalInertia(mass, localInertia);
        }

        Transform startTransform = new Transform();
        startTransform.setIdentity();
        startTransform.origin.set(new Vector3f(0, -a, 0));

        view = Primitives.getBox(a, 1);
        view.setName("Box");
        MotionState myMotionState = new JpctMotionState(view);
        myMotionState.setWorldTransform(startTransform);
//        MotionState myMotionState = new DefaultMotionState(startTransform);

        RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(mass, myMotionState, colShape, localInertia);
        rbInfo.restitution = 1.0f;
        model = new RigidBody(rbInfo);
    }

    public RigidBody getModel() {
        return model;
    }

    public Object3D getView() {
        return view;
    }
}