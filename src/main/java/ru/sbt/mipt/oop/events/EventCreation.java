package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.events.processors.RandomEventProcessor;

public class EventCreation {
    private final RandomEventProcessor eventProcessor;
    private final GettingNextSensorEvent eventNext;

    public EventCreation(RandomEventProcessor eventProcessor, GettingNextSensorEvent eventNext) {
        this.eventProcessor = eventProcessor;
        this.eventNext = eventNext;
    }

    public void createEvent() {
        SensorEvent event = eventNext.getNextEvent();
        while (event != null) {
            System.out.println("Got event: " + event);
            eventProcessor.processEvent(event);
            event = eventNext.getNextEvent();
        }
    }
}
