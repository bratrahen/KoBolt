package com.helloworld;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.kobot.jbullet.FullObject;
import com.threed.jpct.*;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;
import java.awt.*;

public class Sphere implements FullObject {
    private final RigidBody model;
    private final Object3D view;

    private final float radius;
    private final float mass;

    public Sphere(float mass, float radius) {
        this.radius = radius;
        this.mass = mass;

        CollisionShape shape = getCollisionShape();
        Vector3f localInertia = calculateInertia(shape);

        Transform startTransform = new Transform();
        startTransform.setIdentity();
        startTransform.origin.set(new Vector3f(-30, 50, 0));

        view = Primitives.getSphere(radius);
        view.setName(Sphere.class.getSimpleName()+ "@" + view.getID());

        TextureManager.getInstance().addTexture("red",   new Texture(2, 2, new Color(255, 0, 0)));
        view.setTexture("red");
        MotionState myMotionState = new JpctMotionState(view);


        myMotionState.setWorldTransform(startTransform);
        RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(mass, myMotionState, shape, localInertia);
        rbInfo.linearDamping = 0.0f;
        rbInfo.angularDamping = 0.0f;

        model = new RigidBody(rbInfo);
    }

    @NotNull
    private Vector3f calculateInertia(CollisionShape shape) {
        Vector3f localInertia = new Vector3f(0, 0, 0);
        boolean isDynamic = this.mass != 0;
        if (isDynamic){
            shape.calculateLocalInertia(this.mass, localInertia);
        }
        return localInertia;
    }

    @NotNull
    private SphereShape getCollisionShape() {
        return new SphereShape(radius);
    }

    public RigidBody getModel() {
        return model;
    }

    public Object3D getView() {
        return view;
    }
}
