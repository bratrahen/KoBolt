package com.kobot.framework.entitysystem;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.entitysystem.components.factory.PrimitivesFactory;
import com.kobot.framework.entitysystem.manager.EntityManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class StubPrimitivesFactory extends PrimitivesFactory {
    public StubPrimitivesFactory(EntityManager entityManager, float scale) {
        super(entityManager, scale);
    }

    @NotNull
    @Override
    protected RendererComponent createSphereRenderer(float radiusInMeters, Color color) {
        return new StubRenderer();
    }

    @NotNull
    @Override
    protected RendererComponent createCubeRenderer(float sideInMeters, @NotNull Color color) {
        return new StubRenderer();
    }
}

class StubRenderer implements RendererComponent{

    public MotionState createMotionState() {
        return new DefaultMotionState();
    }

    @NotNull
    public CollisionShape getBoundingBox() {
        return null;
    }
}
