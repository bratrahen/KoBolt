package com.kobot.framework.entitysystem.eventbus;

import java.util.ArrayList;
import java.util.List;

public class StubListener implements GameEventListener {
    public int eventCount;
    public List<GameEvent> events = new ArrayList<GameEvent>();

    public void handle(GameEvent event) {
        eventCount++;
        events.add(event);
    }
}
