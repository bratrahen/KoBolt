package com.kobot.framework.entitysystem;

import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.entitysystem.components.factory.EntityFactory;
import com.kobot.framework.entitysystem.manager.EntityManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class StubEntityFactory extends EntityFactory{
    public StubEntityFactory(EntityManager entityManager) {
        super(entityManager);
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
}
