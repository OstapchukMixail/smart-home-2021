package ru.sbt.mipt.oop.events.processors;


import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.signaling.Alarm;
import ru.sbt.mipt.oop.signaling.AlarmReactor;
import ru.sbt.mipt.oop.action.AlarmToAlertAction;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.AlarmEvent;

public class EventProcessorDecorator implements EventProcessor, AlarmReactor {
    private EventProcessor processor;
    private Alarm alarm;
    private Event event;
    private final String message = "Attention";

    public EventProcessorDecorator(EventProcessor processor, Alarm alarm) {
        this.processor = processor;
        this.alarm = alarm;
    }

    @Override
    public void onAlarmActiveState() {
        if (event instanceof AlarmEvent) {
            Action component = new AlarmToAlertAction();
            component.run(event);
            System.out.println(message);
        } else {
            processor.processEvent(event);
        }
    }

    @Override
    public void onAlarmInactiveState() {
        processor.processEvent(event);
    }

    @Override
    public void onAlarmAlertState() {
        System.out.println(message);
    }

    @Override
    public void processEvent(Event event) {
        this.event = event;
        alarm.react(this);
    }
}