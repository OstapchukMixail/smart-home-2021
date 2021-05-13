package ru.sbt.mipt.oop.events.processors;

import ru.sbt.mipt.oop.events.SensorEvent;

public interface EventProcessor {
    void processEvent(SensorEvent event);
}
