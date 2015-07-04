package com.kobot.framework.entitysystem.manager;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.Component;
import com.kobot.framework.entitysystem.components.ComponentStub;
import com.kobot.framework.entitysystem.components.SubComponentStub;
import com.kobot.framework.entitysystem.manager.EntityManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EntityManagerTest {

    private EntityManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new EntityManager();
    }

    @Test
    public void EntityManager_AddTwiceSameInstanceToDifferentEntities_AddedToBothEntities() {
        ComponentStub component = new ComponentStub();
        Entity entity1 = new Entity(1);
        Entity entity2 = new Entity(2);

        manager.addComponentToEntity(component, entity1);
        manager.addComponentToEntity(component, entity2);

        Collection<Component> all = manager.getAllComponentsOfClass(ComponentStub.class);
        assertEquals(1, all.size());
        assertTrue(all.contains(component));

        Collection<Component> components1 = manager.getComponentsForEntity(ComponentStub.class, entity1);
        assertEquals(1, components1.size());
        assertTrue(components1.contains(component));

        Collection<Component> components2 = manager.getComponentsForEntity(ComponentStub.class, entity2);
        assertEquals(1, components2.size());
        assertTrue(components2.contains(component));
    }

    @Test
    public void EntityManager_AddTwiceSameInstanceToSameEntity_AddedOnce() {
        ComponentStub ComponentStub = new ComponentStub();
        Entity entity = new Entity(1);

        manager.addComponentToEntity(ComponentStub, entity);
        manager.addComponentToEntity(ComponentStub, entity);

        Collection<Component> all = manager.getAllComponentsOfClass(ComponentStub.class);
        assertEquals(1, all.size());
        assertTrue(all.contains(ComponentStub));
    }

    @Test
    public void EntityManager_AddTwiceSameComponentStubIdToDifferentEntities_ComponentsAddedToManager() {
        ComponentStub ComponentStub1_instance1 = new ComponentStub();
        ComponentStub ComponentStub1_instance2 = new ComponentStub();

        manager.addComponentToEntity(ComponentStub1_instance1, new Entity(1));
        manager.addComponentToEntity(ComponentStub1_instance2, new Entity(2));

        Collection<Component> all = manager.getAllComponentsOfClass(ComponentStub.class);
        assertEquals(2, all.size());
        assertTrue(all.contains(ComponentStub1_instance1));
        assertTrue(all.contains(ComponentStub1_instance2));
    }

    @Test
    public void EntityManager_AddTwiceDifferentComponentStubIdToSameEntity_ComponentsAddedToManager() {
        ComponentStub ComponentStub1_instance1 = new ComponentStub();
        ComponentStub ComponentStub1_instance2 = new ComponentStub();
        Entity entity = new Entity(1);

        manager.addComponentToEntity(ComponentStub1_instance1, entity);
        manager.addComponentToEntity(ComponentStub1_instance2, entity);

        Collection<Component> all = manager.getAllComponentsOfClass(ComponentStub.class);
        assertEquals(2, all.size());
        assertTrue(all.contains(ComponentStub1_instance1));
        assertTrue(all.contains(ComponentStub1_instance2));
    }

    @Test
    public void EntityManager_PutSubclassGetSuperclass() {
        SubComponentStub subInstance = new SubComponentStub();
        Entity entity = new Entity(1);

        manager.addComponentToEntity(subInstance, entity);

        Collection<Component> all = manager.getAllComponentsOfClass(ComponentStub.class);
        assertEquals(1, all.size());
        assertTrue(all.contains(subInstance));
    }

    @Test
    public void getEntityForComponent(){
        ComponentStub component = new ComponentStub();
        Entity entity = new Entity(1);

        manager.addComponentToEntity(component, entity);
        Set<Entity> all = manager.getEntitiesForComponent(component);
        assertEquals(1, all.size());
    }
}