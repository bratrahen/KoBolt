package com.kobot.framework.entitysystem.components.factory;

import com.kobot.framework.ModelLoader;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.api.Body;
import com.kobot.framework.entitysystem.components.MaxLifeSpan;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.RangedWeapon;
import com.kobot.framework.entitysystem.components.body.PrimitiveBody;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.entitysystem.components.ai.MotherShipAi;
import com.kobot.framework.objects.physics.Box;
import com.kobot.framework.objects.physics.GameObject;
import com.kobot.framework.objects.physics.Sphere;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;
import java.awt.*;

public abstract class PrimitivesFactory {
    public final EntityManager entityManager;

    public PrimitivesFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @NotNull
    public Entity createStaticSphere(float radiusInMeters, @NotNull Color color, @NotNull Vector3f position) {
        return createDynamicSphere(GameObject.STATIC_OBJECT, radiusInMeters, color, position);
    }

    @NotNull
    public Entity createDynamicSphere(float massInKilograms, float radiusInMeters, @NotNull Color color, @NotNull Vector3f position) {
        RendererComponent renderer = createSphereRenderer(radiusInMeters, color);
        Sphere sphere = new Sphere(massInKilograms, radiusInMeters, position, renderer.createMotionState());
        Body physics = new PrimitiveBody(sphere.getRigidBody());

        Entity entity = entityManager.createEntity();
        entityManager.addComponentToEntity(renderer, entity);
        entityManager.addComponentToEntity(physics, entity);

        return entity;
    }

    @NotNull
    protected abstract RendererComponent createSphereRenderer(float radiusInMeters, Color color);

    @NotNull
    public Entity createStaticCube(float sideInMeters, Color color, Vector3f position) {
        return createDynamicCube(GameObject.STATIC_OBJECT, sideInMeters, color, position);
    }

    @NotNull
    public Entity createDynamicCube(float massInKilograms, float sideInMeters, @NotNull Color color, @NotNull Vector3f position) {
        RendererComponent renderer = createCubeRenderer(sideInMeters, color);

        Box cube = new Box(massInKilograms, sideInMeters, position, renderer.createMotionState());
        Body simulator = new PrimitiveBody(cube.getRigidBody());
        Entity entity = entityManager.createEntity();
        entityManager.addComponentToEntity(renderer, entity);
        entityManager.addComponentToEntity(simulator, entity);
        return entity;
    }

    @NotNull
    protected abstract RendererComponent createCubeRenderer(float sideInMeters, @NotNull Color color);

    public Entity createCannonBall(Vector3f start) {
        Entity cannonBall = createDynamicSphere(10, 1, Color.LIGHT_GRAY, start);
        entityManager.addComponentToEntity(new MaxLifeSpan(1.0f), cannonBall);
        return cannonBall;
    }

}
