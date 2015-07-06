package com.kobot.framework.entitysystem.manager;

import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.StubUniqueComponent;
import com.kobot.framework.entitysystem.components.api.basic.Component;
import org.junit.Before;
import org.junit.Test;

public class UniqueComponentTest {
    private EntityManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new EntityManager();
    }

    @Test(expected = IllegalArgumentException.class)
    public void UniqueComponent_AddTwiceToSameEntity_Exception() {
        Entity entity = new Entity(1);
        Component component = new StubUniqueComponent();

        manager.addComponentToEntity(component, entity);
        manager.addComponentToEntity(component, entity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void UniqueComponent_AddTwoInstancesToSameEntity_Exception() {
        Entity entity = new Entity(1);
        Component component1 = new StubUniqueComponent();
        Component component2 = new StubUniqueComponent();

        manager.addComponentToEntity(component1, entity);
        manager.addComponentToEntity(component2, entity);
    }
}
