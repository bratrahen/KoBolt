package com.monsterwars;

import com.kobot.framework.ModelLoader;
import com.kobot.framework.entitysystem.components.JpctRendererComponent;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class JpctShipFactory extends ShipFactory {
    private final ModelLoader loader = new ModelLoader("F:\\1.ProgramowanieProjekty\\_resources\\models");

    public JpctShipFactory(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected RendererComponent createSphereRenderer(float radiusInMeters, Color color) {
        Object3D object3d = Primitives.getSphere(radiusInMeters);
        object3d.setName("Sphere@" + object3d.getID());
        applyColorTexture(object3d, color);
        return new JpctRendererComponent(object3d);
    }

    protected void applyColorTexture(@NotNull Object3D object, @NotNull Color color) {
        String textureId = "#" + color.getRGB() + "#" + color.getAlpha();
        boolean textureExists = TextureManager.getInstance().containsTexture(textureId);
        if (!textureExists) {
            TextureManager.getInstance().addTexture(textureId, new Texture(10, 10, color));
        }
        object.setTexture(textureId);
    }

    @Override
    @NotNull
    protected RendererComponent createCubeRenderer(float sideInMeters, @NotNull Color color) {
        float halfExtend = sideInMeters / 2.0f;
        Object3D object3d = Primitives.getCube(halfExtend);
        object3d.setName("Box@" + object3d.getID());
        applyColorTexture(object3d, color);

        return new JpctRendererComponent(object3d);
    }

    protected RendererComponent createKushanCarrierRenderer(){
        return new JpctRendererComponent(loader.load("Homeworld\\Kushan\\Carrier\\lod0", "Carrier", 1.0f));
    }

    @Override
    protected RendererComponent createTaiidanCarrierRenderer() {
        return new JpctRendererComponent(loader.load("Homeworld\\Taiidan\\Carrier-T\\lod0", "Carrier", 1.0f));
    }

    @Override
    protected RendererComponent createKushanAttackBomberRenderer() {
        return new JpctRendererComponent(loader.load("Homeworld\\Kushan\\Attack Bomber\\lod0", "Attackbomber", 1.0f));
    }

    @Override
    protected RendererComponent createTaiidanAttackBomberRenderer() {
        return new JpctRendererComponent(loader.load("Homeworld\\Taiidan\\Attack Bomber-T\\lod0", "Attackbomber", 1.0f));
    }

    @Override
    protected RendererComponent createKushanAssaultFrigateRenderer() {
        return new JpctRendererComponent(loader.load("Homeworld\\Kushan\\Assault Frigate\\lod0", "Standardfrigate", 1.0f));
    }

    @Override
    protected RendererComponent createTaiidanAssaultFrigateRenderer() {
        return new JpctRendererComponent(loader.load("Homeworld\\Taiidan\\Assault Frigate-T\\lod0", "Standardfrigate", 1.0f));
    }
}


