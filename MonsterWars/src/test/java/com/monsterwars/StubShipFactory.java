package com.monsterwars;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.entitysystem.manager.BaseComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.monsterwars.components.ai.ShipAi;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.vecmath.Vector3f;
import java.awt.*;

public class StubShipFactory extends ShipFactory{
    public StubShipFactory(EntityManager entityManager, float scale) {
        super(entityManager, scale);
    }

    public Entity createStubShip(Vector3f position, Vector3f orientation){
        float mass = 10;
        Entity ship = createShip(mass, position, orientation, new StubRendererComponent(new Vector3f(1, 1, 1)));
        entityManager.addComponentToEntity(new ShipAi(), ship);
        return ship;
    }

    @Override
    protected RendererComponent createKushanAttackBomberRenderer() {
        return new StubRendererComponent(new Vector3f(1f, 0.1f, 0.33f));
    }

    @Override
    protected RendererComponent createTaiidanAttackBomberRenderer() {
        return new StubRendererComponent(new Vector3f(1f, 0.1f, 0.33f));
    }

    @Override
    protected RendererComponent createKushanAssaultFrigateRenderer() {
        return new StubRendererComponent(new Vector3f(30f, 10f, 10f));
    }

    @Override
    protected RendererComponent createTaiidanAssaultFrigateRenderer() {
        return new StubRendererComponent(new Vector3f(30f, 10f, 10f));
    }

    @Override
    protected RendererComponent createKushanCarrierRenderer() {
        return new StubRendererComponent(new Vector3f(100f, 20f, 40f));
    }

    @Override
    protected RendererComponent createTaiidanCarrierRenderer() {
        return new StubRendererComponent(new Vector3f(100f, 20f, 40f));
    }
}
