package com.kobot.framework.entitysystem.components.factory;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.MaxLifeSpan;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.simulation.PhysicalObject;
import com.kobot.framework.simulation.PhysicalObjectBuilder;
import com.kobot.framework.simulation.PhysicalObjectFactory;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;
import java.awt.*;

public abstract class PrimitivesFactory extends PhysicalObjectFactory{
    public static final float MASS_OF_STATIC_OBJECT = 0.0f;

    public final EntityManager entityManager;

    public PrimitivesFactory(EntityManager entityManager, float scale) {
        super(scale);
        this.entityManager = entityManager;
    }

    @NotNull
    protected abstract RendererComponent createCubeRenderer(float sideInMeters, @NotNull Color color);

    @NotNull
    protected abstract RendererComponent createSphereRenderer(float radiusInMeters, Color color);

    /**
     * @param radius measured in meters [m]
     * @param color color of the cube
     * @param position world coordinated [x, y, z] measured in meters [m]
     * @return
     */
    @NotNull
    public Entity createStaticSphere(float radius, @NotNull Color color, @NotNull Vector3f position) {
        return createDynamicSphere(MASS_OF_STATIC_OBJECT, radius, color, position);
    }

    /**
     * @param mass measured in kilograms [kg]
     * @param radius measured in meters [m]
     * @param color color of the cube
     * @param position world coordinated [x, y, z] measured in meters [m]
     * @return
     */
    @NotNull
    public Entity createDynamicSphere(float mass, float radius, @NotNull Color color, @NotNull Vector3f position) {
        RendererComponent renderer = createSphereRenderer(radius, color);

        CollisionShape shape = CollisionShapeFactory.createSphereShape(radius);
        PhysicalObjectBuilder builder = createBuilder();
        builder.setShape(shape).setMass(mass).setPosition(position).setRestitution(1.0f);
        PhysicalObject physicalObject = builder.build(renderer.createMotionState());

        Entity entity = entityManager.createEntity();
        entityManager.addComponentToEntity(renderer, entity);
        entityManager.addComponentToEntity(physicalObject, entity);

        return entity;
    }

    /**
     * @param size measured in meters [m]
     * @param color color of the cube
     * @param position world coordinated [x, y, z] measured in meters [m]
     * @return
     */
    @NotNull
    public Entity createStaticCube(float size, Color color, Vector3f position) {
        return createDynamicCube(MASS_OF_STATIC_OBJECT, size, color, position);
    }

    /**
     * @param mass measured in kilograms [kg]
     * @param size measured in meters [m]
     * @param color color of the cube
     * @param position world coordinated [x, y, z] measured in meters [m]
     * @return
     */
    @NotNull
    public Entity createDynamicCube(float mass, float size, @NotNull Color color, @NotNull Vector3f position) {
        RendererComponent renderer = createCubeRenderer(size, color);

        CollisionShape shape = CollisionShapeFactory.createBoxShape(new Vector3f(size, size, size));
        PhysicalObjectBuilder builder = createBuilder();
        builder.setShape(shape).setMass(mass).setPosition(position).setRestitution(1.0f);
        PhysicalObject physicalObject = builder.build(renderer.createMotionState());

        Entity entity = entityManager.createEntity();
        entityManager.addComponentToEntity(renderer, entity);
        entityManager.addComponentToEntity(physicalObject, entity);
        return entity;
    }

    public Entity createCannonBall(Vector3f start) {
        Entity cannonBall = createDynamicSphere(10, 1, Color.GREEN, start);
        entityManager.addComponentToEntity(new MaxLifeSpan(60.0f), cannonBall);
        return cannonBall;
    }

}
