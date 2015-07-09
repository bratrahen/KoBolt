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

    public JpctShipFactory(EntityManager entityManager, float scale) {
        super(entityManager, scale);
    }

    protected RendererComponent createKushanCarrierRenderer(){
        return new JpctRendererComponent(loader.load("Homeworld\\Kushan\\Carrier\\lod0", "Carrier", scale));
    }

    @Override
    protected RendererComponent createTaiidanCarrierRenderer() {
        return new JpctRendererComponent(loader.load("Homeworld\\Taiidan\\Carrier-T\\lod0", "Carrier", scale));
    }

    @Override
    protected RendererComponent createKushanAttackBomberRenderer() {
        return new JpctRendererComponent(loader.load("Homeworld\\Kushan\\Attack Bomber\\lod0", "Attackbomber", scale));
    }

    @Override
    protected RendererComponent createTaiidanAttackBomberRenderer() {
        return new JpctRendererComponent(loader.load("Homeworld\\Taiidan\\Attack Bomber-T\\lod0", "Attackbomber", scale));
    }

    @Override
    protected RendererComponent createKushanAssaultFrigateRenderer() {
        return new JpctRendererComponent(loader.load("Homeworld\\Kushan\\Assault Frigate\\lod0", "Standardfrigate", scale));
    }

    @Override
    protected RendererComponent createTaiidanAssaultFrigateRenderer() {
        return new JpctRendererComponent(loader.load("Homeworld\\Taiidan\\Assault Frigate-T\\lod0", "Standardfrigate", scale));
    }
}


