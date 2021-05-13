package ru.sbt.mipt.oop.events.processors;

import ru.sbt.mipt.oop.events.SensorEvent;
import java.util.List;


public class RandomEventProcessor implements EventProcessor {
    private final List<EventProcessor> processors;

    public RandomEventProcessor(List<EventProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public void processEvent(SensorEvent event){
        for (EventProcessor processor: processors) {
            processor.processEvent(event);
        }
    }
}