package com.kobot.framework.entitysystem;

import com.kobot.framework.entitysystem.components.RangedWeapon;
import com.kobot.framework.entitysystem.components.PhysicsComponent;
import com.kobot.framework.entitysystem.components.Team;
import com.kobot.framework.entitysystem.components.ai.MotherShipAi;

import java.util.Collection;

public class ComponentFinder {
    private final EntityManager entityManager;

    public ComponentFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Team findTeam(Entity entity) {
        return (Team)entityManager.getComponentForEntity(Team.class, entity);
    }

    public PhysicsComponent findPhysicalBody(Entity entity) {
        return (PhysicsComponent)entityManager.getComponentForEntity(PhysicsComponent.class, entity);
    }

    public RangedWeapon findGun(Entity entity) {
        return (RangedWeapon)entityManager.getComponentForEntity(RangedWeapon.class, entity);
    }

    public Collection<MotherShipAi> findAllMotherShipsAi(){
        return (Collection)entityManager.getAllComponentsOfClass(MotherShipAi.class);
    }
}