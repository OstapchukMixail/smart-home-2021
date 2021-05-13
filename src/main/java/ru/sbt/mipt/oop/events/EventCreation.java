package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.events.processors.*;

public class EventCreation {
    private final EventProcessor eventProcessor;
    private final GettingNextSensorEvent eventNext;

    public EventCreation(EventProcessor eventProcessor, GettingNextSensorEvent eventNext) {
        this.eventProcessor = eventProcessor;
        this.eventNext = eventNext;
    }

    public void createEvent() {
        SensorEvent event = eventNext.getNextEvent();
        while (event != null) {
            System.out.println("Got event: " + event);
            Event event1 = (Event) event;
            eventProcessor.processEvent(event1);
            event = eventNext.getNextEvent();
        }
    }
}
