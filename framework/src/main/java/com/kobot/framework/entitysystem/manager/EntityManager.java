package com.kobot.framework.entitysystem.manager;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.kobot.framework.entitysystem.Entity;
import com.kobot.framework.entitysystem.components.Component;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EntityManager {
    Collection<Long> entities = new ArrayList<Long>();
    Map<Class, BiMap<Long, Set<Component>>> componentsByEntityByClass = new HashMap<Class, BiMap<Long, Set<Component>>>();


    long lowestUnassignedId = 1;

    public Entity createEntity() {
        Entity entity = new Entity(generateNewId());
        entities.add(entity.getId());
        return entity;
    }

    private long generateNewId() {
        if (lowestUnassignedId == Long.MAX_VALUE) {
            throw new RuntimeException("No free Entity Id available");
        }
        return lowestUnassignedId++;
    }

    public void removeEntity(@NotNull Entity entity) {
        for (Map<Long, Set<Component>> componentsByEntity : componentsByEntityByClass.values()) {
            componentsByEntity.remove(entity.getId());
        }
        entities.remove(entity.getId());
    }

    public void addComponentToEntity(@NotNull Component component, @NotNull Entity entity) {


        Set<Class> allTypes = getGeneralizations(component.getClass());
        for (Class  type : allTypes ){
            BiMap<Long, Set<Component>> componentsByEntity = componentsByEntityByClass.get(type);
            if (componentsByEntity == null) {
                componentsByEntity = HashBiMap.create();
                componentsByEntityByClass.put(type, componentsByEntity);
            }

            Set<Component> components = componentsByEntity.get(entity.getId());
            if (components == null){
                components = new HashSet<Component>();
                componentsByEntity.put(entity.getId(), components);
            }
            components.add(component);
        }
    }

    /**
     * Builds an <b>unordered</b> set of all interface and object classes that
     * are generalizations of the provided class.
     * @param classObject the class to find generalization of.
     * @return a Set of class objects.
     * @author http://www.java2s.com/Tutorial/Java/0125__Reflection/GetSuperInterfaces.htm
     */
    private Set<Class> getGeneralizations(Class classObject) {
        Set<Class> generalizations = new HashSet();

        generalizations.add(classObject);

        Class superClass = classObject.getSuperclass();
        if (superClass != null) {
            generalizations.addAll(getGeneralizations(superClass));
        }

        Class[] superInterfaces = classObject.getInterfaces();
        for (int i = 0; i < superInterfaces.length; i++) {
            Class superInterface = superInterfaces[i];
            generalizations.addAll(getGeneralizations(superInterface));
        }
        generalizations.remove(Component.class);
        generalizations.remove(Object.class);
        return generalizations;
    }

    Set<Component> getComponentsForEntity(@NotNull Class clazz, @NotNull Entity entity) {
        return componentsByEntityByClass.get(clazz).get(entity.getId());
    }

    Set<Entity> getAllEntitiesPossessingComponentOfClass(Class clazz) {
        Map<Long, Set<Component>> componentsByEntity = componentsByEntityByClass.get(clazz);
        if (componentsByEntity != null) {
            Set<Entity> result = new HashSet<Entity>();
            for (Long id : componentsByEntity.keySet()) {
                result.add(new Entity(id));
            }
            return result;
        } else {
            return new HashSet<Entity>();
        }
    }

    Collection<Component> getAllComponentsOfClass(Class clazz) {
        if (componentsByEntityByClass.containsKey(clazz)) {
            Set<Set<Component>> componentsPerEntity = componentsByEntityByClass.get(clazz).values();

            HashSet<Component> result = new HashSet<Component>();
            for (Set<Component> components : componentsPerEntity){
                result.addAll(components);
            }

            return result;
        } else {
            return new HashSet<Component>();
        }
    }

    Set<Entity> getEntitiesForComponent(Component component) {
        Set<Entity> result = new HashSet<Entity>();

        BiMap<Long, Set<Component>> componentsByEntity = componentsByEntityByClass.get(component.getClass());
        for (Long id : componentsByEntity.keySet()) {
            Set<Component> components = componentsByEntity.get(id);
            if (components.contains(component)){
                result.add(new Entity(id));
            }
        }

        return result;
    }
}
