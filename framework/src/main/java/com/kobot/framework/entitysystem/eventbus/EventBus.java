package com.kobot.framework.entitysystem.eventbus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventBus {

    private static EventBus instance;
    private Map<Class, Set<GameEventListener>> listenersByEventClass = new HashMap<Class, Set<GameEventListener>>();

    private EventBus() {
    }

    public static void raiseEvent(GameEvent event) {
        if (getInstance().listenersByEventClass.containsKey(event.getClass())) {
            Set<GameEventListener> listeners = getInstance().listenersByEventClass.get(event.getClass());
            for (GameEventListener listener : listeners) {
                listener.handle(event);
            }
        }
    }

    private static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public static void addListener(GameEventListener listener, Class eventClass) {
        Set<GameEventListener> listeners = getInstance().listenersByEventClass.get(eventClass);
        if (listeners == null) {
            listeners = new HashSet<GameEventListener>();
            getInstance().listenersByEventClass.put(eventClass, listeners);
        }
        listeners.add(listener);
    }

    public static void reset() {
        instance = new EventBus();
    }
}
