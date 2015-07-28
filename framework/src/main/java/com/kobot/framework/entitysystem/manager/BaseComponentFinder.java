package com.kobot.framework.entitysystem.manager;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.*;
import com.kobot.framework.entitysystem.components.ai.MotherShipAi;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import com.kobot.framework.simulation.PhysicalObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@SuppressWarnings("unchecked")
public class BaseComponentFinder {
    private final EntityManager entityManager;

    public BaseComponentFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
}

    protected Object findFirst(Class clazz, Entity entity) {
        return entityManager.getComponentsForEntity(clazz, entity).iterator().next();
    }

    protected Set find(Class clazz, Entity entity) {
        return (Set) entityManager.getComponentsForEntity(clazz, entity);
    }

    protected Set findAll(Class clazz) {
        return (Set) entityManager.getAllComponentsOfClass(clazz);
    }

    @NotNull
    public Team findTeam(Entity entity) {
        return (Team)findFirst(Team.class, entity);
    }

    @NotNull
    public PhysicalObject findPhysicalObject(Entity entity) {
        return (PhysicalObject) findFirst(PhysicalObject.class, entity);
    }

    @NotNull
    public Set<RangedWeapon> findRangedWeapons(Entity entity) {
        return find(RangedWeapon.class, entity);
    }

    @NotNull
    public Set<MotherShipAi> findAllMotherShipsAi() {
        return findAll(MotherShipAi.class);
    }

    @NotNull
    public Set<RendererComponent> findRenderers(Entity entity) {
        return find(RendererComponent.class, entity);
    }

    @NotNull
    public RendererComponent findRenderer(Entity entity) {
        return (RendererComponent)findFirst(RendererComponent.class, entity);
    }

    public Set<MaxLifeSpan> findAllMaxLifeSpans() {
        return findAll(MaxLifeSpan.class);
    }

    public Set<JetEngine> findAllJetEngines() {
        return findAll(JetEngine.class);
    }

    public HealthComponent findHealthComponent(Entity entity) {
        return (HealthComponent) findFirst(HealthComponent.class, entity);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Set<HealthComponent> findAllHealth() {
        return findAll(HealthComponent.class);
    }
}