package com.kobot.framework.entitysystem;

import com.kobot.framework.entitysystem.components.Component;
import com.kobot.framework.entitysystem.components.JpctRendererComponent;
import com.kobot.framework.entitysystem.components.RendererComponent;
import com.kobot.framework.entitysystem.systems.JpctRenderingSystem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityManager {
    Collection<Long> entities = new ArrayList<Long>();
    Map<Class, Map<Long, Component>> componentsByEntityByClass = new HashMap<Class, Map<Long, Component>>();
    long lowestUnassignedId = 1;

    public Entity createEntity() {
        Entity entity = new Entity(generateNewId());
        entities.add(entity.getId());
        return entity;
    }

    long generateNewId() {
        if (lowestUnassignedId == Long.MAX_VALUE) {
            throw new RuntimeException("No free Entity Id available");
        }
        return lowestUnassignedId++;
    }

    public void removeEntity(@NotNull Entity entity) {
        for (Map<Long, Component> componentsByEntity : componentsByEntityByClass.values()) {
            componentsByEntity.remove(entity.getId());
        }
        entities.remove(entity.getId());
    }

    public void addComponentToEntity(@NotNull Component component, @NotNull Entity entity) {
        Map<Long, Component> componentsByEntity = componentsByEntityByClass.get(component.getClass());
        if (componentsByEntity == null) {
            componentsByEntity = new HashMap<Long, Component>();
            componentsByEntityByClass.put(component.getClass(), componentsByEntity);
        }
        componentsByEntity.put(entity.getId(), component);
    }

    public Component getComponentForEntity(@NotNull Class clazz, @NotNull Entity entity) {
        return componentsByEntityByClass.get(clazz).get(entity.getId());
    }

    public Collection<Entity> getAllEntitiesPossessingComponentOfClass(Class clazz) {
        Map<Long, Component> componentsByEntity = componentsByEntityByClass.get(clazz);
        if (componentsByEntity != null) {
            Collection<Entity> result = new ArrayList<Entity>();
            for (Long id : componentsByEntity.keySet()) {
                result.add(new Entity(id));
            }
            return result;
        } else {
            return new ArrayList<Entity>();
        }
    }

    public void getAllComponentsOfClass(Class clazz) {

    }
}
