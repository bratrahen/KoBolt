package com.kobot.framework.entitysystem.components.ai;

import com.kobot.framework.entitysystem.ComponentFinder;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.EntityManager;
import com.kobot.framework.entitysystem.components.RangedWeapon;
import com.kobot.framework.entitysystem.components.PhysicsComponent;
import com.kobot.framework.entitysystem.components.Team;

import java.util.Collection;

public class MotherShipAi implements AiComponent {
    private final EntityManager entityManager;
    private final ComponentFinder finder;

    public MotherShipAi(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.finder = new ComponentFinder(entityManager);
    }

    public void update(float timestepInSeconds) {
        Entity myEntity = entityManager.getEntityForComponent(this);

        RangedWeapon myGun = finder.findGun(myEntity);
        myGun.reduceCooldown(timestepInSeconds);
        if (!myGun.canFire()){
            return;
        }

        Team myTeam = finder.findTeam(myEntity);
        PhysicsComponent myBody = finder.findPhysicalBody(myEntity);

        Collection<Entity> friendsAndFoes = entityManager.getAllEntitiesPossessingComponentOfClass(Team.class);
        for (Entity friendOrFoe : friendsAndFoes) {
            Team team = finder.findTeam(friendOrFoe);
            if (team != myTeam) {
                PhysicsComponent enemyBody = finder.findPhysicalBody(friendOrFoe);
                myGun.fireAt(myBody.getPosition(), enemyBody.getPosition());
            }
        }
    }
}
