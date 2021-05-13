package ru.sbt.mipt.oop.events.processors;

import ru.sbt.mipt.oop.events.Event;

public interface EventProcessor {
    void processEvent(Event event);
}
