package com.kobot.framework.entitysystem.eventbus;

import org.junit.Test;
import static org.junit.Assert.*;

public class EventBusTest {

    @Test
    public void testRaiseEvent() throws Exception {
        StubListener listener = new StubListener();

        EventBus.addListener(listener, StubGameEvent_1.class);

        EventBus.raiseEvent(new StubGameEvent_1());
        EventBus.raiseEvent(new StubGameEvent_1());
        EventBus.raiseEvent(new StubGameEvent_2());
        EventBus.raiseEvent(new StubGameEvent_1());

        assertEquals(3, listener.eventCount);
    }
}

class StubGameEvent_1 implements GameEvent{}
class StubGameEvent_2 implements GameEvent{}

class StubListener implements GameEventListener {
    public int eventCount;

    public void handle(GameEvent event) {
        eventCount++;
    }
}