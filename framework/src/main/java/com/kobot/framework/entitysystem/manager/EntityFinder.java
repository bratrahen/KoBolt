package com.kobot.framework.entitysystem.manager;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.Team;
import com.kobot.framework.entitysystem.components.api.basic.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EntityFinder {
    private final EntityManager entityManager;
    private final BaseComponentFinder componentFinder;

    public EntityFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.componentFinder = new BaseComponentFinder(entityManager);
    }

    public Set<Entity> findAllEntitiesPossessingComponentOfClass(Class clazz) {
        return entityManager.getAllEntitiesPossessingComponentOfClass(clazz);
    }

    @NotNull
    public Entity findEntityForComponent(Component component) {
        return entityManager.getEntitiesForComponent(component).iterator().next();
    }

    public Set<Entity> findEnemies(Entity entity) {
        Set<Entity> enemies = new HashSet<Entity>();

        Team myTeam = componentFinder.findTeam(entity);
        Collection<Entity> friendsAndFoes = componentFinder.getEntityManager().getAllEntitiesPossessingComponentOfClass(Team.class);
        for (Entity friendOrFoe : friendsAndFoes) {
            Team hisTeam = componentFinder.findTeam(friendOrFoe);
            if (hisTeam != myTeam) {
                enemies.add(friendOrFoe);
            }
        }

        return enemies;
    }
}