package com.monsterwars;


import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.Team;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.entitysystem.eventbus.EventBus;
import com.kobot.framework.entitysystem.eventbus.events.CreateEntityEvent;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.simulation.PhysicalObject;
import com.kobot.framework.simulation.PhysicalObjectBuilder;
import com.kobot.framework.simulation.PhysicalObjectFactory;

import javax.vecmath.Vector3f;

public abstract class ShipFactory extends PhysicalObjectFactory{
    private static final float PI = (float)Math.PI;
    private static final float BOMBER_MASS_IN_KG = 10;
    private static final float CARRIER_MASS_IN_KG = 10000;
    private static final float FRIGATE_MASS_IN_KG = 1000;

    public final EntityManager entityManager;

    public ShipFactory(EntityManager entityManager, float scale) {
        super(scale);
        this.entityManager = entityManager;
    }

    protected abstract RendererComponent createKushanAttackBomberRenderer();

    protected abstract RendererComponent createTaiidanAttackBomberRenderer();

    protected abstract RendererComponent createKushanAssaultFrigateRenderer();

    protected abstract RendererComponent createTaiidanAssaultFrigateRenderer();

    protected abstract RendererComponent createKushanCarrierRenderer();

    protected abstract RendererComponent createTaiidanCarrierRenderer();

    public Entity createKushanAttackBomber(Vector3f position, Vector3f orientation, long teamId) {
        RendererComponent renderer = createKushanAttackBomberRenderer();
        return createAttackBomber(position, orientation, teamId, renderer);
    }

    public Entity createTaiidanAttackBomber(Vector3f position, Vector3f orientation, long teamId) {
        RendererComponent renderer = createTaiidanAttackBomberRenderer();
        return createAttackBomber(position, orientation, teamId, renderer);
    }

    protected Entity createAttackBomber(Vector3f position, Vector3f orientation, long teamId, RendererComponent renderer) {
        Entity ship = createShip(BOMBER_MASS_IN_KG, position, orientation, renderer);
        entityManager.addComponentToEntity(Team.getById(teamId), ship);
        return ship;
    }

    public Entity createKushanAssaultFrigate(Vector3f position, Vector3f orientation, long teamId) {
        RendererComponent renderer = createKushanAssaultFrigateRenderer();
        return createAssaultFrigate(position, orientation, teamId, renderer);
    }

    public Entity createTaiidanAssaultFrigate(Vector3f position, Vector3f orientation, long teamId) {
        RendererComponent renderer = createTaiidanAssaultFrigateRenderer();
        return createAssaultFrigate(position, orientation, teamId, renderer);
    }

    private Entity createAssaultFrigate(Vector3f position, Vector3f orientation, long teamId, RendererComponent renderer) {
        Entity ship = createShip(FRIGATE_MASS_IN_KG, position, orientation, renderer);
        entityManager.addComponentToEntity(Team.getById(teamId), ship);
        return ship;
    }

    public Entity createKushanCarrier(Vector3f position, Vector3f orientation, long teamId) {
        RendererComponent renderer = createKushanCarrierRenderer();
        return createCarrier(position, orientation, teamId, renderer);
    }

    public Entity createTaiidanCarrier(Vector3f position, Vector3f orientation, long teamId) {
        RendererComponent renderer = createTaiidanCarrierRenderer();
        return createCarrier(position, orientation, teamId, renderer);
    }

    private Entity createCarrier(Vector3f position, Vector3f orientation, long teamId, RendererComponent renderer) {
        Entity ship = createShip(CARRIER_MASS_IN_KG, position, orientation, renderer);
        entityManager.addComponentToEntity(Team.getById(teamId), ship);
        return ship;
    }

    /**
     * @param mass measured in kilograms [kg]
     * @param position world coordinated [x, y, z] measured in meters [m]
     * @param orientation counterclockwise rotation [rotX, rotY, rotZ] around world axises measured in radians [rad]
     * @param renderer ship's renderer
     * @return Entity
     */
    protected Entity createShip(float mass, Vector3f position, Vector3f orientation, RendererComponent renderer) {
        PhysicalObjectBuilder builder = createBuilder();
        builder.setShape(renderer.getBoundingBox());
        builder.setMass(mass).setPosition(position).setOrientation(orientation);
        PhysicalObject physicalObject = builder.build(renderer.createMotionState());

        Entity entity = entityManager.createEntity();
        entityManager.addComponentToEntity(physicalObject, entity);
        entityManager.addComponentToEntity(renderer, entity);

        EventBus.raiseEvent(new CreateEntityEvent(entity));
        return entity;
    }
}
