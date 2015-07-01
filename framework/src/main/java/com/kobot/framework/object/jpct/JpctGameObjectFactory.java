package com.kobot.framework.object.jpct;

import com.kobot.framework.object.Box;
import com.kobot.framework.object.GameObject;
import com.kobot.framework.object.Sphere;
import com.kobot.framework.object.common.GameObjectFactory;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;

import javax.vecmath.Vector3f;
import java.awt.*;

public class JpctGameObjectFactory implements GameObjectFactory {
    public Sphere createDynamicSphere(float massInKilograms, float radiusInMeters, Color color, Vector3f position) {
        Object3D object3d = Primitives.getSphere(radiusInMeters);
        object3d.setName(Sphere.class.getSimpleName() + "@" + object3d.getID());
        applyColorTexture(object3d, color);

        return new Sphere(massInKilograms, radiusInMeters, position, new JpctMotionState(object3d));
    }

    private void applyColorTexture(Object3D object, Color color) {
        String textureId = "#" + color.getRGB() + "#" + color.getAlpha();
        boolean textureExists = TextureManager.getInstance().containsTexture(textureId);
        if (!textureExists) {
            TextureManager.getInstance().addTexture(textureId, new Texture(10, 10, color));
        }

        object.setTexture(textureId);
    }

    public Box createStaticCube(float sideInMeters, Color color, Vector3f position) {
        return createDynamicCube(GameObject.STATIC_OBJECT, sideInMeters, color, position);
    }

    public Box createDynamicCube(float massInKilograms, float sideInMeters, Color color, Vector3f position) {
        float halfExtend = sideInMeters / 2.0f;
        Object3D object3d = Primitives.getCube(halfExtend);
        object3d.setName(Box.class.getSimpleName() + "@" + object3d.getID());
        applyColorTexture(object3d, color);

        return new Box(massInKilograms, sideInMeters, position, new JpctMotionState(object3d));
    }
}
