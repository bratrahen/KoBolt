package com.kobot.framework.entitysystem.manager;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.StubComponent;
import com.kobot.framework.entitysystem.components.StubSubComponent;
import com.kobot.framework.entitysystem.components.api.basic.Component;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntityManagerTest {

    private EntityManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new EntityManager();
    }

    @Test
    public void getEntitiesForComponent_ForComponentThatWasNotAdded_EmptySet() {
        Set<Entity> entities = manager.getEntitiesForComponent(new StubComponent());
        assertTrue(entities.isEmpty());
    }

    @Test
    public void getComponentsForEntity_ForComponentClassThatWasNotAdded_EmptySet() {
        Set<Component> components = manager.getComponentsForEntity(StubComponent.class, new Entity(1));
        assertTrue(components.isEmpty());
    }

    @Test
    public void getComponentsForEntity_ForComponentThatWasAddedAndEntityThatWasNotAdded_EmptySet() {
        manager.addComponentToEntity(new StubComponent(), new Entity(1));
        Set<Component> components = manager.getComponentsForEntity(StubComponent.class, new Entity(2));
        assertTrue(components.isEmpty());
    }

    @Test
    public void EntityManager_AddTwiceSameComponentStubIdToDifferentEntities_ComponentsAddedToManager() {
        StubComponent stubComponent1_instance1 = new StubComponent();
        StubComponent stubComponent1_instance2 = new StubComponent();

        manager.addComponentToEntity(stubComponent1_instance1, new Entity(1));
        manager.addComponentToEntity(stubComponent1_instance2, new Entity(2));

        Collection<Component> all = manager.getAllComponentsOfClass(StubComponent.class);
        assertEquals(2, all.size());
        assertTrue(all.contains(stubComponent1_instance1));
        assertTrue(all.contains(stubComponent1_instance2));
    }

    @Test
    public void EntityManager_AddTwiceDifferentComponentStubIdToSameEntity_ComponentsAddedToManager() {
        StubComponent stubComponent1_instance1 = new StubComponent();
        StubComponent stubComponent1_instance2 = new StubComponent();
        Entity entity = new Entity(1);

        manager.addComponentToEntity(stubComponent1_instance1, entity);
        manager.addComponentToEntity(stubComponent1_instance2, entity);

        Collection<Component> all = manager.getAllComponentsOfClass(StubComponent.class);
        assertEquals(2, all.size());
        assertTrue(all.contains(stubComponent1_instance1));
        assertTrue(all.contains(stubComponent1_instance2));
    }

    @Test
    public void EntityManager_PutSubclassGetSuperclass() {
        StubSubComponent subInstance = new StubSubComponent();
        Entity entity = new Entity(1);

        manager.addComponentToEntity(subInstance, entity);

        Collection<Component> all = manager.getAllComponentsOfClass(StubComponent.class);
        assertEquals(1, all.size());
        assertTrue(all.contains(subInstance));
    }

    @Test
    public void getEntityForComponent() {
        StubComponent component = new StubComponent();
        Entity entity = new Entity(1);

        manager.addComponentToEntity(component, entity);
        Set<Entity> all = manager.getEntitiesForComponent(component);
        assertEquals(1, all.size());
    }
}