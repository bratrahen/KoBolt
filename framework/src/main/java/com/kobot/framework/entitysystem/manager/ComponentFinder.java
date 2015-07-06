package com.kobot.framework.entitysystem.manager;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.*;
import com.kobot.framework.entitysystem.components.ai.MotherShipAi;
import com.kobot.framework.entitysystem.components.api.basic.Component;
import com.kobot.framework.entitysystem.components.api.RendererComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ComponentFinder {
    private final EntityManager entityManager;

    public ComponentFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
}

    private Object findFirst(Class clazz, Entity entity) {
        return entityManager.getComponentsForEntity(clazz, entity).iterator().next();
    }

    private Set find(Class clazz, Entity entity) {
        return (Set) entityManager.getComponentsForEntity(clazz, entity);
    }

    private Set findAll(Class clazz) {
        return (Set) entityManager.getAllComponentsOfClass(clazz);
    }

    @NotNull
    public Team findTeam(Entity entity) {
        return (Team)findFirst(Team.class, entity);
    }

    @NotNull
    public Set<PhysicsComponent> findPhysicalBodies(Entity entity) {
        return find(PhysicsComponent.class, entity);
    }

    @NotNull
    public PhysicsComponent findSinglePhysicalBody(Entity entity) {
        return (PhysicsComponent) findFirst(PhysicsComponent.class, entity);
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

    public Set<Entity> findAllEntitiesPossessingComponentOfClass(Class clazz) {
        return entityManager.getAllEntitiesPossessingComponentOfClass(clazz);
    }

    public Set<Entity> findEntitiesForComponent(Component component) {
        return entityManager.getEntitiesForComponent(component);
    }

    public Set<Entity> getAllEntitiesPossessingComponentOfClass(Class clazz) {
        return entityManager.getAllEntitiesPossessingComponentOfClass(clazz);
    }

    public Set<Component> getComponentsForEntity(Class clazz, Entity entity){
        return entityManager.getComponentsForEntity(clazz, entity);
    }

    public Set<Entity> findEnemies(Entity entity) {
        Set<Entity> enemies = new HashSet<Entity>();

        Team myTeam = findTeam(entity);
        Collection<Entity> friendsAndFoes = entityManager.getAllEntitiesPossessingComponentOfClass(Team.class);
        for (Entity friendOrFoe : friendsAndFoes) {
            Team hisTeam = findTeam(friendOrFoe);
            if (hisTeam != myTeam) {
                enemies.add(friendOrFoe);
            }
        }

        return  enemies;
    }
}