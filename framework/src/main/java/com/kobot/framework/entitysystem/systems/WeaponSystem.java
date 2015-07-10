package com.kobot.framework.entitysystem.systems;

import com.bulletphysics.collision.dispatch.CollisionWorld;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.HealthComponent;
import com.kobot.framework.entitysystem.eventbus.events.AttackEvent;
import com.kobot.framework.entitysystem.eventbus.EventBus;
import com.kobot.framework.entitysystem.eventbus.GameEvent;
import com.kobot.framework.entitysystem.eventbus.GameEventListener;
import com.kobot.framework.entitysystem.manager.EntityManager;
import com.kobot.framework.simulation.PhysicalObject;
import com.kobot.framework.simulation.RayCaster;

public class WeaponSystem extends System implements GameEventListener {
    private final RayCaster rayCaster;

    public WeaponSystem(EntityManager entityManager, RayCaster rayCaster) {
        super(entityManager);
        this.rayCaster = rayCaster;
        EventBus.addListener(this, AttackEvent.class);
    }

    public void handle(GameEvent event) {
        if (event instanceof AttackEvent) {
            AttackEvent attack = (AttackEvent) event;
            CollisionWorld.ClosestRayResultCallback resultCallback = rayCaster.cast(attack.from, attack.to);
            if (resultCallback.hasHit()) {
                PhysicalObject physicalObject = (PhysicalObject) resultCallback.collisionObject.getUserPointer();
                Entity entity = entityFinder.findEntityForComponent(physicalObject);
                HealthComponent health = componentFinder.findHealthComponent(entity);
                health.currentHealth -= attack.damage;
            }
        }
    }

    @Override
    public void update(float timeStepInSeconds) {

    }
}
