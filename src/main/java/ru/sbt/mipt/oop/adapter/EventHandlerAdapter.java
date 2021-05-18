package ru.sbt.mipt.oop.adapter;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.processors.EventProcessor;

import java.util.Map;

public class EventHandlerAdapter implements EventHandler {
    private final EventProcessor processor;
    private final Map<String, EventType> dict;

    public EventHandlerAdapter(EventProcessor processor, Map<String, EventType> dict) {
        this.processor = processor;
        this.dict = dict;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        EventType eventType = dict.get(event.getEventType());
        if (eventType == null) return;

        processor.processEvent((Event) new SensorEvent(eventType, event.getObjectId()));
    }
}
