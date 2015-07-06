package com.kobot.framework.entitysystem.manager;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.StubComponent;
import com.kobot.framework.entitysystem.components.api.basic.Component;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ComponentTest {
    private EntityManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new EntityManager();
    }

    @Test (expected = IllegalArgumentException.class)
    public void Component_AddTwiceSameInstanceToSameEntity_Exception() {
        Entity entity = new Entity(1);
        Component component = new StubComponent();

        manager.addComponentToEntity(component, entity);
        manager.addComponentToEntity(component, entity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void NotSharedComponent_AddSameInstanceToTwoEntities_Exception() {
        Entity entity1 = new Entity(1);
        Entity entity2 = new Entity(2);
        Component component = new StubComponent();

        manager.addComponentToEntity(component, entity1);
        manager.addComponentToEntity(component, entity2);
    }

    @Test
    public void NotUniqueComponent_AddTwoInstancesToSameEntity_GetTwoInstances() {
        Entity entity = new Entity(1);
        Component component1 = new StubComponent();
        Component component2 = new StubComponent();

        manager.addComponentToEntity(component1, entity);
        manager.addComponentToEntity(component2, entity);

        Set<Component> components = manager.getComponentsForEntity(StubComponent.class, entity);
        assertEquals(2, components.size());
        assertTrue(components.contains(component1));
        assertTrue(components.contains(component2));
    }
}
