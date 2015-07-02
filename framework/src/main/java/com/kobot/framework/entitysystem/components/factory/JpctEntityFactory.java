package com.kobot.framework.entitysystem.components.factory;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.EntityManager;
import com.kobot.framework.entitysystem.components.RendererComponent;
import com.kobot.framework.entitysystem.components.PhysicsComponent;
import com.kobot.framework.entitysystem.components.JpctRendererComponent;
import com.kobot.framework.objects.graphic.jpct.JpctMotionState;
import com.kobot.framework.objects.physics.Box;
import com.kobot.framework.objects.physics.GameObject;
import com.kobot.framework.objects.physics.Sphere;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;

import javax.vecmath.Vector3f;
import java.awt.*;

public class JpctEntityFactory {

    private final EntityManager entityManager;

    public JpctEntityFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Entity createDynamicSphere(float massInKilograms, float radiusInMeters, Color color, Vector3f position) {
        Object3D object3d = Primitives.getSphere(radiusInMeters);
        object3d.setName(Sphere.class.getSimpleName() + "@" + object3d.getID());
        applyColorTexture(object3d, color);

        RendererComponent RendererComponent = new JpctRendererComponent(object3d);
        Sphere sphere = new Sphere(massInKilograms, radiusInMeters, position, RendererComponent.createMotionState());
        PhysicsComponent physicsComponent = new PhysicsComponent(sphere.getRigidBody());

        Entity entity = entityManager.createEntity();
        entityManager.addComponentToEntity(RendererComponent, entity);
        entityManager.addComponentToEntity(physicsComponent, entity);

        return entity;
    }

    public Entity createStaticCube(float sideInMeters, Color color, Vector3f position) {
        return createDynamicCube(GameObject.STATIC_OBJECT, sideInMeters, color, position);
    }

    public Entity createDynamicCube(float massInKilograms, float sideInMeters, Color color, Vector3f position) {
        float halfExtend = sideInMeters / 2.0f;
        Object3D object3d = Primitives.getCube(halfExtend);
        object3d.setName(Box.class.getSimpleName() + "@" + object3d.getID());
        applyColorTexture(object3d, color);

        RendererComponent RendererComponent = new JpctRendererComponent(object3d);
        Box cube = new Box(massInKilograms, sideInMeters, position, new JpctMotionState(object3d));
        PhysicsComponent physicsComponent = new PhysicsComponent(cube.getRigidBody());

        Entity entity = entityManager.createEntity();
        entityManager.addComponentToEntity(RendererComponent, entity);
        entityManager.addComponentToEntity(physicsComponent, entity);

        return entity;
    }

    private void applyColorTexture(Object3D object, Color color) {
        String textureId = "#" + color.getRGB() + "#" + color.getAlpha();
        boolean textureExists = TextureManager.getInstance().containsTexture(textureId);
        if (!textureExists) {
            TextureManager.getInstance().addTexture(textureId, new Texture(10, 10, color));
        }
        object.setTexture(textureId);
    }
}
