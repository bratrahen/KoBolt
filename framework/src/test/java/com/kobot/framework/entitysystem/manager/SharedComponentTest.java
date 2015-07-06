package com.kobot.framework.entitysystem.manager;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.StubComponent;
import com.kobot.framework.entitysystem.components.StubSharedComponent;
import com.kobot.framework.entitysystem.components.api.basic.Component;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SharedComponentTest {

    private EntityManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new EntityManager();
    }

    @Test
    public void SharedComponent_AddSameInstanceToTwoEntities_GetOneForEachEntity() {
        Entity entity1 = new Entity(1);
        Entity entity2 = new Entity(2);
        StubSharedComponent component = new StubSharedComponent();

        manager.addComponentToEntity(component, entity1);
        manager.addComponentToEntity(component, entity2);

        Set<Component> components1 = manager.getComponentsForEntity(StubSharedComponent.class, entity1);
        Set<Component> components2 = manager.getComponentsForEntity(StubSharedComponent.class, entity2);

        assertEquals(1, components1.size());
        assertTrue(components1.contains(component));

        assertEquals(1, components2.size());
        assertTrue(components2.contains(component));
    }

}
