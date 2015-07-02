package com.kobot.framework.entitysystem;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.kobot.framework.entitysystem.components.Component;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EntityManager {
    Collection<Long> entities = new ArrayList<Long>();
    Map<Class, BiMap<Long, Component>> componentsByEntityByClass = new HashMap<Class, BiMap<Long, Component>>();

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
        BiMap<Long, Component> componentsByEntity = componentsByEntityByClass.get(component.getClass());
        if (componentsByEntity == null) {
            componentsByEntity = HashBiMap.create();
            componentsByEntityByClass.put(component.getClass(), componentsByEntity);
        }

        componentsByEntity.put(entity.getId(), component);
    }

    public boolean hasComponent(@NotNull Class clazz, @NotNull Entity entity) {
        if (!componentsByEntityByClass.containsKey(clazz)) {
            return false;
        } else {
            return componentsByEntityByClass.get(clazz).containsKey(entity.getId());
        }
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

    public Collection<Component> getAllComponentsOfClass(Class clazz) {
        if (componentsByEntityByClass.containsKey(clazz)){
            return componentsByEntityByClass.get(clazz).values();
        } else {
            return new HashSet<Component>();
        }
    }

    public Entity getEntityForComponent(Component component) {
        if (componentsByEntityByClass.containsKey(component.getClass())){
            BiMap<Long, Component> componentsByEntity = componentsByEntityByClass.get(component.getClass());
            long id = componentsByEntity.inverse().get(component);
            return new Entity(id);
        } else {
            throw new IllegalArgumentException("Componetn not registerd in EntityManager");
        }
    }
}
