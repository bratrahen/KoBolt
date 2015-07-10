package com.kobot.framework.entitysystem.systems;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.StubPrimitivesFactory;
import com.kobot.framework.entitysystem.components.HealthComponent;
import com.kobot.framework.entitysystem.eventbus.events.AttackEvent;
import com.kobot.framework.entitysystem.manager.ComponentFinder;
import com.kobot.framework.entitysystem.manager.EntityManager;
import org.junit.Test;

import javax.vecmath.Vector3f;
import java.awt.*;

import static org.junit.Assert.assertEquals;

public class WeaponSystemTest {
    @Test
    public void testHandle() throws Exception {
        EntityManager manager = new EntityManager();
        ComponentFinder finder = new ComponentFinder(manager);
        StubPrimitivesFactory factory = new StubPrimitivesFactory(manager);

        PhysicsSystem physicsSystem = new PhysicsSystem(manager, -10f, 1f);
        WeaponSystem weaponSystem = new WeaponSystem(manager, physicsSystem.createRayCaster());

        Entity sphere = factory.createDynamicSphere(1f, 1f, Color.RED, new Vector3f(5f, 0f, 0f));
        HealthComponent health = new HealthComponent(100, 100);
        manager.addComponentToEntity(health, sphere);

        physicsSystem.update(1f);

        weaponSystem.handle(new AttackEvent(new Vector3f(), new Vector3f(10f, 0, 0), 10f));
        assertEquals(90, health.currentHealth);
    }
}