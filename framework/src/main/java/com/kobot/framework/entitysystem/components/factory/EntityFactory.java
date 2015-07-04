package com.kobot.framework.entitysystem.components.factory;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.RangedWeapon;
import com.kobot.framework.entitysystem.components.PhysicsComponent;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.entitysystem.components.ai.MotherShipAi;
import com.kobot.framework.objects.physics.Box;
import com.kobot.framework.objects.physics.GameObject;
import com.kobot.framework.objects.physics.Sphere;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;
import java.awt.*;

public abstract class EntityFactory {
    public final EntityManager entityManager;

    public EntityFactory(EntityManager entityManager) {
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
        PhysicsComponent physics = new PhysicsComponent(sphere.getRigidBody());

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
        PhysicsComponent simulator = new PhysicsComponent(cube.getRigidBody());
        Entity entity = entityManager.createEntity();
        entityManager.addComponentToEntity(renderer, entity);
        entityManager.addComponentToEntity(simulator, entity);
        return entity;
    }

    public Entity createStaticCubeWithGun(float sideInMeters, @NotNull Color color, @NotNull Vector3f position){
        final float DAMAGE = 10.0f;
        final float RELOAD_IN_SEC= 2.0f;

        Entity entity = createStaticCube(sideInMeters, color, position);
        entityManager.addComponentToEntity(new RangedWeapon(DAMAGE, RELOAD_IN_SEC, this), entity);
        entityManager.addComponentToEntity(new MotherShipAi(entityManager), entity);
        return entity;
    }

    @NotNull
    protected abstract RendererComponent createCubeRenderer(float sideInMeters, @NotNull Color color);
}
